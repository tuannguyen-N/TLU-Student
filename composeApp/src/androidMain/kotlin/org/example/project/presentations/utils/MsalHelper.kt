package org.example.project.presentations.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException
import org.example.project.R

object MsalHelper {
    private var msalApp: ISingleAccountPublicClientApplication? = null

    private val SCOPES = arrayOf("api://77df993d-e728-4b8e-9770-6b409aa99552/access_as_user")

    fun init(context: Context, onReady: () -> Unit) {
        PublicClientApplication.createSingleAccountPublicClientApplication(
            context,
            R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    msalApp = application
                    onReady()
                }
                override fun onError(exception: MsalException) {
                    Log.e("MSAL", "Lỗi khởi tạo: ${exception.message}")
                }
            }
        )
    }

    /**
     * Đăng nhập 2 trong 1: Vừa đăng nhập vừa lấy Access Token cho API
     */
    fun signIn(activity: Activity, onResult: (IAccount?, String?) -> Unit) {
        val app = msalApp ?: return onResult(null, null)

        app.signIn(activity, null, SCOPES, object : AuthenticationCallback {
            override fun onSuccess(result: IAuthenticationResult) {
                val accessToken = result.accessToken
                accessToken.chunked(200).forEachIndexed { index, chunk ->
                    Log.d("MSAL", "$chunk")
                }
                // Lấy Access Token trực tiếp từ kết quả đăng nhập
                onResult(result.account, accessToken)
            }

            override fun onError(exception: MsalException) {
                Log.e("MSAL", "❌ Lỗi đăng nhập: ${exception.message}")
                onResult(null, null)
            }

            override fun onCancel() {
                Log.d("MSAL", "⚠ Người dùng đã hủy")
                onResult(null, null)
            }
        })
    }

    /**
     * Tự động đăng nhập (Không hiện UI):
     * Dùng khi app vừa mở để kiểm tra xem phiên làm việc cũ còn không.
     */
    fun checkExistingAccount(onResult: (IAccount?, String?) -> Unit) {
        val app = msalApp ?: return onResult(null, null)

        app.getCurrentAccountAsync(object : ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                if (activeAccount == null) {
                    onResult(null, null)
                    return
                }

                // Nếu có tài khoản, thử lấy token mới một cách im lặng
                app.acquireTokenSilentAsync(SCOPES, activeAccount.authority, object : SilentAuthenticationCallback {
                    override fun onSuccess(result: IAuthenticationResult) {
                        onResult(result.account, result.accessToken)
                    }
                    override fun onError(exception: MsalException) {
                        onResult(null, null)
                    }
                })
            }
            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {}
            override fun onError(exception: MsalException) = onResult(null, null)
        })
    }

    fun signOut(onResult: (Boolean) -> Unit) {
        msalApp?.signOut(object : ISingleAccountPublicClientApplication.SignOutCallback {
            override fun onSignOut() {
                Log.d("MSAL", "✅ Đã đăng xuất")
                onResult(true)
            }
            override fun onError(exception: MsalException) {
                Log.e("MSAL", "❌ Lỗi đăng xuất: ${exception.message}")
                onResult(false)
            }
        })
    }
}