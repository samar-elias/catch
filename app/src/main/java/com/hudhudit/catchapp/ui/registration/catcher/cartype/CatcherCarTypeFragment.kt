package com.hudhudit.catchapp.ui.registration.catcher.cartype

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CarType
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CountryId
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcherCarTypeBinding
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatcherCarTypeFragment : BaseFragment() {

    private lateinit var binding: FragmentCatcherCarTypeBinding
    private lateinit var registrationActivity: RegistrationActivity
    private val viewModel by viewModels<CatcherCarTypeViewModel>()
    private lateinit var carTypes: MutableList<CarType>
    var carType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatcherCarTypeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegistrationActivity) {
            registrationActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCarTypes()
        onClick()
    }

    private fun onClick(){
        binding.navigateBack.setOnClickListener { findNavController().popBackStack() }
        binding.taxi.setOnClickListener {
            binding.taxiSelectedLayout.visibility = View.VISIBLE
            binding.privateCarSelectedLayout.visibility = View.GONE
            carType = carTypes[0].id
        }
        binding.privateCar.setOnClickListener {
            binding.taxiSelectedLayout.visibility = View.GONE
            binding.privateCarSelectedLayout.visibility = View.VISIBLE
            carType = carTypes[1].id
        }
        binding.nextBtn.setOnClickListener {
            AppConstants.catcherSignUp.cart_type = carType
            findNavController().navigate(R.id.action_catcherCarTypeFragment_to_carInformationFragment2)
        }
    }

    private fun getCarTypes(){
        val countryId = CountryId(AppConstants.catcherSignUp.country_id)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCarTypes(countryId)
        viewModel.carTypesStatus.observe(viewLifecycleOwner){
            when (it.status){
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data!!.results.size > 0){
                       if (it.data.results[1] == null){
                           binding.onlyTaxiSelectedLayout.visibility = View.VISIBLE
                           binding.taxiSelectedLayout.visibility = View.GONE
                           binding.privateCarSelectedLayout.visibility = View.GONE
                           carType = it.data.results[0]!!.id
                       }else{
                           binding.onlyTaxiSelectedLayout.visibility = View.GONE
                           binding.taxiSelectedLayout.visibility = View.VISIBLE
                           binding.privateCarSelectedLayout.visibility = View.GONE
                           carType = it.data.results[0]!!.id
                       }
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    //Toast.makeText(registrationActivity, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}