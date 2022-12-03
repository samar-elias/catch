package com.hudhudit.catchapp.core.base

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hudhudit.catchapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale(this,prefs.language ?: "en")

        val conf = resources.configuration
        conf.locale = Locale.getDefault()
        baseContext.resources.updateConfiguration(
            conf,
            baseContext.resources.displayMetrics
        )


    }


    open fun setLocale(activity: Activity, languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.title = ""
    }

}
