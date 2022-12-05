package com.hudhudit.catchapp.retrofit.services

import com.hudhudit.catchapp.apputils.modules.BooleanResponse
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

}