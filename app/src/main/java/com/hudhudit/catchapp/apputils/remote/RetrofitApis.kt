package com.hudhudit.catchapp.apputils.remote

import com.hudhudit.catchapp.apputils.models.introduction.IntroData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApis {

    @GET("user_intro")
    fun getIntro(): Call<IntroData>
}