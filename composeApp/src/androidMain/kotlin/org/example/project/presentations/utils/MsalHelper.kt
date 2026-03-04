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
import com.microsoft.identity.client.exception.MsalException
import org.example.project.R

object MsalHelper {
    private var msalApp: ISingleAccountPublicClientApplication? = null

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
                    Log.e("MSAL", "Init error: ${exception.message}")
                }
            }
        )
    }

    fun signIn(activity: Activity, onResult: (String?) -> Unit) {
        msalApp?.signIn(
            activity,
            null,
            arrayOf("User.Read"),
            object : AuthenticationCallback {
                override fun onSuccess(authenticationResult: IAuthenticationResult) {
                    val token = authenticationResult.accessToken
                    Log.d("MSAL", "✅ Access Token: $token")
                    onResult(token)
                }
                override fun onError(exception: MsalException) {
                    Log.e("MSAL", "❌ Sign in error: ${exception.message}")
                    onResult(null)
                }
                override fun onCancel() {
                    Log.d("MSAL", "⚠️ Cancelled")
                    onResult(null)
                }
            }
        )
    }

    fun signOut(onResult: (Boolean) -> Unit) {
        msalApp?.getCurrentAccountAsync(object : ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                if (activeAccount == null) {
                    Log.d("MSAL", "⚠️ No account to sign out")
                    onResult(false)
                    return
                }
                msalApp?.signOut(object : ISingleAccountPublicClientApplication.SignOutCallback {
                    override fun onSignOut() {
                        Log.d("MSAL", "✅ Signed out successfully")
                        onResult(true)
                    }
                    override fun onError(exception: MsalException) {
                        Log.e("MSAL", "❌ Sign out error: ${exception.message}")
                        onResult(false)
                    }
                })
            }
            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {}
            override fun onError(exception: MsalException) {
                Log.e("MSAL", "❌ Get account error: ${exception.message}")
                onResult(false)
            }
        })
    }
}