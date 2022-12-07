package com.hudhudit.catchapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUser
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserResponse
import com.hudhudit.catchapp.ui.introduction.IntroductionActivity
import com.hudhudit.catchapp.ui.main.MainActivity
import com.hudhudit.catchapp.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                getUserFromSharedPreferences()
            }, 2000)
    }

    private fun getUserFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF_KEY, MODE_PRIVATE)
        val id = sharedPreferences.getString(AppConstants.ID_KEY, null)
        val type = sharedPreferences.getString(AppConstants.TYPE_KEY, null)
        val gson = Gson()
        val user = sharedPreferences.getString(AppConstants.USER_KEY, null)
        when {
            id != null && !type.isNullOrEmpty()-> {
                if (type == "0"){
                    AppConstants.catcheeUser = gson.fromJson(user, CatcheeUserResponse::class.java)
                    AppConstants.userType = type
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }else if (type == "1"){
                    AppConstants.catcherUser = gson.fromJson(user, CatcherUserResponse::class.java)
                    AppConstants.userType = type
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }
            else -> {
                val registrationIntent = Intent(this, IntroductionActivity::class.java)
                startActivity(registrationIntent)
                finish()
            }
        }
    }
}