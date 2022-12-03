package com.hudhudit.catchapp.retrofit.services

import com.hudhudit.catchapp.apputils.modules.catchee.CatcheeUserPost
import com.hudhudit.catchapp.apputils.modules.catchee.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.introduction.IntroData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @GET("user_intro")
    suspend fun getIntro(): Response<IntroData>

    @POST("create_client_account")
    suspend fun catcheeCreateAccount(catcheeUserPost: CatcheeUserPost): Response<CatcheeUserResponse>
}