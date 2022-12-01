package com.hudhudit.catchapp.views.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.appdefs.AppDefs
import com.hudhudit.catchapp.apputils.helpers.Helpers
import com.hudhudit.catchapp.apputils.models.introduction.Intro
import com.hudhudit.catchapp.apputils.models.introduction.IntroData
import com.hudhudit.catchapp.apputils.remote.RetrofitApis
import com.hudhudit.catchapp.databinding.ActivityIntroductionBinding
import com.hudhudit.catchapp.databinding.IntroSliderLayoutBinding
import com.hudhudit.catchapp.views.registration.RegistrationActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IntroductionActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntroductionBinding
    var intros: ArrayList<Intro> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getIntroData()
        onClick()
    }

    private fun onClick(){
        binding.continueBtn.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getIntroData(){
        binding.progressBar.visibility = View.VISIBLE
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(AppDefs.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val washerIntroCall: Call<IntroData> =
            retrofit.create(RetrofitApis::class.java).getIntro()
        washerIntroCall.enqueue(object : Callback<IntroData> {
            override fun onResponse(call: Call<IntroData>, response: Response<IntroData>) {
                binding.progressBar.visibility = View.GONE
                intros = response.body()!!.results
                initSlider()
            }

            override fun onFailure(call: Call<IntroData>, t: Throwable) {
                Toast.makeText(this@IntroductionActivity, resources.getString(R.string.internet_connection), Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initSlider() {
        binding.viewPager.visibility = View.VISIBLE
        binding.tabDots.setupWithViewPager(binding.viewPager, true)
        val mAdapter =
            IntroAdapter(intros, this)
        mAdapter.notifyDataSetChanged()
        binding.viewPager.offscreenPageLimit = intros.size
        binding.viewPager.adapter = mAdapter
        Helpers.setSliderTimer(3000, binding.viewPager, mAdapter)
    }
}