package org.example.project.presentations.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.example.project.di.AppContainer
import org.example.project.local.AndroidTokenStorage
import org.example.project.presentations.utils.MsalHelper

class MainActivity : ComponentActivity() {
    lateinit var container: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppContainer()
        initMsal()
        fitSystemWindow()
        hideBottonNavigationBar()
    }

    private fun initAppContainer(){
        val tokenStorage = AndroidTokenStorage(
            context = applicationContext
        )
        container = AppContainer(tokenStorage, applicationContext)
    }

    private fun initMsal(){
        MsalHelper.init(this){
            setContent {
                CompositionLocalProvider(
                    LocalAppContainer provides container
                ) {
                    AppRoot()
                }
            }
        }
    }

    private fun fitSystemWindow(){
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun hideBottonNavigationBar(){
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    }
}