package com.hudhudit.catchapp.utils

import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserPost

class AppConstants {

    companion object{
        const val defaultLanguageKey:String ="DEFAULT_LANGUAGE"
        const val isLoggedInKey:String ="isLoggedInKey"
        const val BaseUrl = "http://driver.hudhudclient.com/Api/"

        lateinit var catcheeSignUp: CatcheeUserPost
    }
}