package com.hudhudit.catchapp.retrofit.repostore

import com.hudhudit.catchapp.apputils.modules.driverlocation.DriverModel
import com.hudhudit.catchapp.utils.Resource


interface MapRepository {
    suspend fun getDriver(result: (Resource<MutableList<DriverModel>>) -> Unit)

}