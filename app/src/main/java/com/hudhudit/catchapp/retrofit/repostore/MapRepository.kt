package com.hudhudit.catchapp.retrofit.repostore

import com.hudhudit.catchapp.apputils.modules.catcher.ChacherUserRequest
import com.hudhudit.catchapp.apputils.modules.driver.adddrive.DriverUserModel

import com.hudhudit.catchapp.utils.Resource


interface MapRepository {
    suspend fun getDriver(result: (Resource<MutableList<DriverUserModel>>) -> Unit)
    suspend fun addDriver(driverUserModel: DriverUserModel, result: (Resource<Pair<DriverUserModel, String>>) -> Unit)
    suspend fun addOrderRequest(chacherUserRequest: ChacherUserRequest, result: (Resource<Pair<ChacherUserRequest, String>>) -> Unit)
    suspend fun deleteRequest(orderId:String, result: (Resource<String>) -> Unit)
    suspend fun updateDriverAvailable(driverUserModel: DriverUserModel,result: (Resource<Pair<DriverUserModel, String>>) -> Unit)
    suspend fun getAllRequest(result: (Resource<MutableList<ChacherUserRequest>>) -> Unit)
    suspend fun updateOrderStatus(status:String,chacherUserRequest: ChacherUserRequest, result: (Resource<Pair<ChacherUserRequest, String>>) -> Unit)


}