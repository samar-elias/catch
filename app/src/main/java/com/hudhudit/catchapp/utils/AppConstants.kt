package com.hudhudit.catchapp.utils

import android.graphics.Bitmap
import android.util.Base64
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserResponse
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserSignUp
import java.io.ByteArrayOutputStream

class AppConstants {

    companion object{
        const val defaultLanguageKey:String ="DEFAULT_LANGUAGE"
        const val isLoggedInKey:String ="isLoggedInKey"
        const val SHARED_PREF_KEY = "SHARED_PREF"
        const val USER_KEY = "User"
        const val TYPE_KEY = "Type"
        const val ID_KEY = "Id"
        const val BaseUrl = "http://driver.hudhudclient.com/Api/"
        const val USER="Users"

        lateinit var catcheeSignUp: CatcheeUserSignUp
        lateinit var catcherSignUp: CatcherUserSignUp
        lateinit var signIn: UserSignIn
        lateinit var catcheeUser: CatcheeUserResponse
        lateinit var catcherUser: CatcherUserResponse
        var userType = ""

        fun convertToBase64(bitmap: Bitmap): String? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }
}