package com.hudhudit.catchapp.retrofit.services

import com.hudhudit.catchapp.apputils.modules.introduction.IntroData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @GET("user_intro")
    suspend fun getIntro(): Response<IntroData>
}