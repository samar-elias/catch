package com.hudhudit.catchapp.retrofit.data

import com.hudhudit.catchapp.apputils.modules.driver.endorder.EndOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.getdriver.GetDriverRequest
import com.hudhudit.catchapp.apputils.modules.driver.order.CreatOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.order.GetOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.order.OrderRequest
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CountryId
import com.hudhudit.catchapp.retrofit.services.NetworkService
import retrofit2.http.Body
import javax.inject.Inject

class MapDataSource @Inject constructor(
    private val networkService: NetworkService
): BaseDataSource() {

    suspend fun cancelClientOrders(token: String, orderRequest: OrderRequest) = getResult{
        networkService.cancelClientOrders(token, orderRequest)
    }

    suspend fun createOrders(token: String, creatOrderRequest: CreatOrderRequest) = getResult{
        networkService.createOrders(token, creatOrderRequest)
    }

    suspend fun getAllDriver(getDriverRequest: GetDriverRequest) = getResult{
        networkService.getAllDriver(getDriverRequest)
    }
    suspend fun endOrders(token: String,endOrderRequest: EndOrderRequest) = getResult{
        networkService.endOrders(token, endOrderRequest)
    }

    suspend fun acceptOrders(token: String,orderRequest: OrderRequest) = getResult{
        networkService.acceptOrders(token, orderRequest)
    }

    suspend fun getOrdersAvailable(getOrderRequest: GetOrderRequest) = getResult{
        networkService.getOrdersAvailable(getOrderRequest)
    }
    suspend fun cancelDriverOrders(token: String, orderRequest: OrderRequest) = getResult{
        networkService.cancelDriverOrders(token, orderRequest)
    }


}