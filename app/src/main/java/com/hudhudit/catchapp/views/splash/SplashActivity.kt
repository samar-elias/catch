package com.hudhudit.catchapp.views.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.views.introduction.IntroductionActivity
import com.hudhudit.catchapp.views.registration.RegistrationActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setSplash()
    }

    private fun setSplash() {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent = Intent(this, IntroductionActivity:: class.java)
                startActivity(intent)
                finish()
            }, 2000)
    }
}