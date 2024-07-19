package dev.pegasus.splashscreenapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.flow.MutableStateFlow

class RoutingActivity : Activity() {

    private val myValue = MutableStateFlow(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        timeout()

        splashScreen.setKeepOnScreenCondition { true }
        /*splashScreen.setKeepOnScreenCondition { myValue.value }
        splashScreen.setOnExitAnimationListener {
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreate: Exit")
            navigateScreen()
        }*/
    }

    private fun timeout() {
        Handler(Looper.getMainLooper()).postDelayed({
            myValue.value = false
            navigateScreen()
        }, 1000)
    }

    private fun navigateScreen() {
        startActivity(Intent(this, ActivityEntrance::class.java))
        finish()
    }
}