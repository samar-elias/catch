package com.hudhudit.catchapp.retrofit.services

import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.driver.endorder.EndOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.getdriver.GetDriverRequest
import com.hudhudit.catchapp.apputils.modules.driver.getdriver.GetDriverResponse
import com.hudhudit.catchapp.apputils.modules.driver.order.CreatOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.order.GetOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.order.GetOrderResponse
import com.hudhudit.catchapp.apputils.modules.driver.order.OrderRequest
import com.hudhudit.catchapp.apputils.modules.catchee.history.CatcheeOrdersHistory
import com.hudhudit.catchapp.apputils.modules.catchee.history.Counts
import com.hudhudit.catchapp.apputils.modules.catchee.notifications.CatcheeNotifications
import com.hudhudit.catchapp.apputils.modules.catcher.history.CatcherOrdersHistory
import com.hudhudit.catchapp.apputils.modules.catcher.notifications.CatcherNotifications
import com.hudhudit.catchapp.apputils.modules.introduction.IntroData
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.registration.Countries
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @GET("user_intro")
    suspend fun getIntro(): Response<IntroData>

    @GET("get_country")
    suspend fun getCountries(): Response<Countries>

    //Catchee registration
    @POST("check_phone_client")
    suspend fun checkCatcheePhone(@Body checkPhone: CheckPhone): Response<BooleanResponse>

    @POST("create_client_account")
    suspend fun catcheeCreateAccount(@Body catcheeUserSignUp: CatcheeUserSignUp): Response<CatcheeUserResponse>

    @POST("client_login")
    suspend fun catcheeSignIn(@Body catcheeUserSignIn: UserSignIn): Response<CatcheeUserResponse>

    //Catcher registration
    @POST("check_phone_driver")
    suspend fun checkCatcherPhone(@Body checkPhone: CheckPhone): Response<BooleanResponse>

    @POST("driver_login")
    suspend fun catcherSignIn(@Body catcherUserSignIn: UserSignIn): Response<CatcherUserResponse>

    @POST("get_car_type")
    suspend fun getCarTypes(@Body countryId: CountryId): Response<CarTypes>

    @GET("get_car_brand")
    suspend fun getCarBrands(): Response<CarBrands>

    @GET("get_car_model/{id}")
    suspend fun getCarModels(@Path(value= "id", encoded=false) id: String): Response<CarModels>

    @POST("create_driver_account")
    suspend fun catcherCreateAccount(@Body catcherUserSignUp: CatcherUserSignUp): Response<CatcherUserResponse>

    //client
    //not work
    @POST("cancel_client_orders")
    suspend fun cancelClientOrders(@Header("Authorization")token: String,@Body orderRequest: OrderRequest): Response<BooleanResponse>
    @POST("create_orders")
    suspend fun createOrders(@Header("Authorization")token: String,@Body creatOrderRequest: CreatOrderRequest): Response<GetDriverResponse>
    //Driver
    @POST("get_all_driver")
    suspend fun getAllDriver(@Body getDriverRequest: GetDriverRequest): Response<GetDriverResponse>

    @POST("end_orders")
    suspend fun endOrders(@Header("Authorization")token: String,@Body endOrderRequest: EndOrderRequest): Response<BooleanResponse>

    @POST("accept_orders")
    suspend fun acceptOrders(@Header("Authorization")token: String,@Body orderRequest: OrderRequest): Response<BooleanResponse>


    @POST("get_orders_available")
    suspend fun getOrdersAvailable(@Body getOrderRequest: GetOrderRequest): Response<GetOrderResponse>
    //not work
    @POST("cancel_driver_orders")
    suspend fun cancelDriverOrders(@Header("Authorization")token: String,@Body orderRequest: OrderRequest): Response<BooleanResponse>




    //Catchee
    @GET("get_notifications_client")
    suspend fun getCatcheeNotifications(@Header("Authorization") token: String, @Query("page") page: String): Response<CatcheeNotifications>

    @GET("get_orders_client_history")
    suspend fun getCatcheeHistory(@Header("Authorization") token: String, @Query("page") page: String): Response<CatcheeOrdersHistory>

    @GET("get_orders_number_and_total_client")
    suspend fun getCatcheeHistoryData(@Header("Authorization") token: String): Response<Counts>

    @GET("get_notifications_driver")
    suspend fun getCatcherNotifications(@Header("Authorization") token: String, @Query("page") page: String): Response<CatcherNotifications>

    @GET("get_orders_driver_history")
    suspend fun getCatcherHistory(@Header("Authorization") token: String, @Query("page") page: String): Response<CatcherOrdersHistory>

    @GET("get_orders_number_and_total_driver")
    suspend fun getCatcherHistoryData(@Header("Authorization") token: String): Response<Counts>



}