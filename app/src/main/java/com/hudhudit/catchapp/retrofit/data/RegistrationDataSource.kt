package com.hudhudit.catchapp.retrofit.data

import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserPost
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CheckPhone
import com.hudhudit.catchapp.retrofit.services.NetworkService
import javax.inject.Inject

class RegistrationDataSource @Inject constructor(
    private val networkService: NetworkService
): BaseDataSource() {

    suspend fun getIntro() = getResult{
        networkService.getIntro()
    }

    suspend fun catcheeCreateAccount(catcheeUserPost: CatcheeUserPost) = getResult{
        networkService.catcheeCreateAccount(catcheeUserPost)
    }

    suspend fun checkPhone(checkPhone: CheckPhone) = getResult{
        networkService.checkPhone(checkPhone)
    }
}