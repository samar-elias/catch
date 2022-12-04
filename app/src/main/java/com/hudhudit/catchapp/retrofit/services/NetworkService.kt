package com.hudhudit.catchapp.retrofit.services

import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.introduction.IntroData
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserResponse
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @GET("user_intro")
    suspend fun getIntro(): Response<IntroData>

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
}