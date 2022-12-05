package com.hudhudit.catchapp.ui.introduction

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.helpers.Helpers
import com.hudhudit.catchapp.apputils.modules.introduction.Intro
import com.hudhudit.catchapp.core.base.BaseActivity
import com.hudhudit.catchapp.databinding.ActivityIntroductionBinding
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async

@AndroidEntryPoint
class IntroductionActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()

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
        viewModel.getIntroData()
        viewModel.introStatus.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    initSlider(it.data!!.results)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
//                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initSlider(intros: MutableList<Intro>) {
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