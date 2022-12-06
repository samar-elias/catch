package com.hudhudit.catchapp.retrofit.data

import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserSignUp
import com.hudhudit.catchapp.retrofit.services.NetworkService
import javax.inject.Inject

class CatcheeDataSource @Inject constructor(
    private val networkService: NetworkService
): BaseDataSource()  {

    suspend fun getCatcheeNotifications(token: String, page: String) = getResult{
        networkService.getCatcheeNotifications(token, page)
    }

    suspend fun getCatcheeHistory(token: String, page: String) = getResult{
        networkService.getCatcheeHistory(token, page)
    }

    suspend fun getHistoryData(token: String) = getResult{
        networkService.getHistoryData(token)
    }

}