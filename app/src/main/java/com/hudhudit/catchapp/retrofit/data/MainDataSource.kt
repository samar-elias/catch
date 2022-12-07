package com.hudhudit.catchapp.retrofit.data

import com.hudhudit.catchapp.retrofit.services.NetworkService
import javax.inject.Inject

class MainDataSource @Inject constructor(
    private val networkService: NetworkService
): BaseDataSource()  {

    suspend fun getCatcheeNotifications(token: String, page: String) = getResult{
        networkService.getCatcheeNotifications(token, page)
    }

    suspend fun getCatcheeHistory(token: String, page: String) = getResult{
        networkService.getCatcheeHistory(token, page)
    }

    suspend fun getCatcheeHistoryData(token: String) = getResult{
        networkService.getCatcheeHistoryData(token)
    }

    suspend fun getCatcherNotifications(token: String, page: String) = getResult{
        networkService.getCatcherNotifications(token, page)
    }

    suspend fun getCatcherHistory(token: String, page: String) = getResult{
        networkService.getCatcherHistory(token, page)
    }

    suspend fun getCatcherHistoryData(token: String) = getResult{
        networkService.getCatcherHistoryData(token)
    }

}