package com.hudhudit.catchapp.retrofit.data

import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserSignIn
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CheckPhone
import com.hudhudit.catchapp.retrofit.services.NetworkService
import javax.inject.Inject

class RegistrationDataSource @Inject constructor(
    private val networkService: NetworkService
): BaseDataSource() {

    suspend fun getIntro() = getResult{
        networkService.getIntro()
    }

    suspend fun checkPhone(checkPhone: CheckPhone) = getResult{
        networkService.checkPhone(checkPhone)
    }

    suspend fun catcheeCreateAccount(catcheeUserSignUp: CatcheeUserSignUp) = getResult{
        networkService.catcheeCreateAccount(catcheeUserSignUp)
    }

    suspend fun catcheeLogin(catcheeUserSignIn: CatcheeUserSignIn) = getResult{
        networkService.catcheeSignIn(catcheeUserSignIn)
    }
}