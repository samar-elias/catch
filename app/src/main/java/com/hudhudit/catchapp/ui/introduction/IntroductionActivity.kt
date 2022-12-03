package com.hudhudit.catchapp.ui.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hudhudit.catchapp.apputils.helpers.Helpers
import com.hudhudit.catchapp.apputils.modules.introduction.Intro
import com.hudhudit.catchapp.databinding.ActivityIntroductionBinding
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntroductionBinding
    var intros: ArrayList<Intro> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        getIntroData()
        onClick()
    }

    private fun onClick(){
        binding.continueBtn.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    private fun getIntroData(){
//        binding.progressBar.visibility = View.VISIBLE
//        val retrofit: Retrofit = Retrofit.Builder().baseUrl(AppDefs.BaseUrl)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//        val washerIntroCall: Call<IntroData> =
//            retrofit.create(RetrofitApis::class.java).getIntro()
//        washerIntroCall.enqueue(object : Callback<IntroData> {
//            override fun onResponse(call: Call<IntroData>, response: Response<IntroData>) {
//                binding.progressBar.visibility = View.GONE
//                intros = response.body()!!.results
//                initSlider()
//            }
//
//            override fun onFailure(call: Call<IntroData>, t: Throwable) {
//                Toast.makeText(this@IntroductionActivity, resources.getString(R.string.internet_connection), Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }

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