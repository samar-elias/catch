package com.hudhudit.catchapp.utils

import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserSignUp

class AppConstants {

    companion object{
        const val defaultLanguageKey:String ="DEFAULT_LANGUAGE"
        const val isLoggedInKey:String ="isLoggedInKey"
        const val BaseUrl = "http://driver.hudhudclient.com/Api/"

        lateinit var catcheeSignUp: CatcheeUserSignUp
        lateinit var catcherSignUp: CatcherUserSignUp
        lateinit var signIn: UserSignIn
    }
}