package org.example.project.presentations.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.IPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.SilentAuthenticationCallback
import com.microsoft.identity.client.exception.MsalClientException
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

    fun signIn(activity: Activity, onResult: (String?, Boolean) -> Unit) {
        val app = msalApp ?: return onResult(null, false)

        app.signIn(activity, null, SCOPES, object : AuthenticationCallback {
            override fun onSuccess(result: IAuthenticationResult) {
                onResult(result.accessToken, false)
            }

            override fun onError(exception: MsalException) {
                Log.e("MSAL", "Lỗi đăng nhập: ${exception.message}")
                onResult(null, exception.isNoInternetError())
            }

            override fun onCancel() {
                onResult(null, false)
            }
        })
    }

    fun checkExistingAccount(onResult: (IAccount?, String?) -> Unit) {
        val app = msalApp ?: return onResult(null, null)

        app.getCurrentAccountAsync(object :
            ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                if (activeAccount == null) {
                    onResult(null, null)
                    return
                }
                app.acquireTokenSilentAsync(
                    SCOPES,
                    activeAccount.authority,
                    object : SilentAuthenticationCallback {
                        override fun onSuccess(result: IAuthenticationResult) {
                            onResult(result.account, result.accessToken)
                        }

                        override fun onError(exception: MsalException) {
                            onResult(null, null)
                        }
                    })
            }

            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {
                if (currentAccount == null) onResult(null, null)
            }

            override fun onError(exception: MsalException) = onResult(null, null)
        })
    }

    fun signOut(onResult: (Boolean) -> Unit) {
        msalApp?.signOut(object : ISingleAccountPublicClientApplication.SignOutCallback {
            override fun onSignOut() {
                Log.d("MSAL", "Đã đăng xuất")
                onResult(true)
            }

            override fun onError(exception: MsalException) {
                Log.e("MSAL", "Lỗi đăng xuất: ${exception.message}")
                onResult(false)
            }
        })
    }

    fun MsalException.isNoInternetError(): Boolean {
        val noInternetKeywords = listOf(
            "ERR_INTERNET_DISCONNECTED",
            "ERR_NAME_NOT_RESOLVED",
            "Unable to resolve host",
            "Network is unreachable"
        )
        return noInternetKeywords.any { message?.contains(it) == true }
                || errorCode == MsalClientException.DEVICE_NETWORK_NOT_AVAILABLE
    }
}