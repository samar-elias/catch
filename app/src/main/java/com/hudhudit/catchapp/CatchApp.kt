package com.hudhudit.catchapp

import android.app.Application
import androidx.viewbinding.BuildConfig

class CatchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
        }
    }
}