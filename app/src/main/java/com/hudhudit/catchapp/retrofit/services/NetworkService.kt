package com.hudhudit.catchapp.retrofit.services

import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserPost
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.introduction.IntroData
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @GET("user_intro")
    suspend fun getIntro(): Response<IntroData>

    @POST("check_phone_client")
    suspend fun checkPhone(@Body checkPhone: CheckPhone): Response<BooleanResponse>

    @POST("create_client_account")
    suspend fun catcheeCreateAccount(catcheeUserPost: CatcheeUserPost): Response<CatcheeUserResponse>
}