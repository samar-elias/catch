package com.hudhudit.catchapp.ui.registration.catcher.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.registration.Country
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserSignUp
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcherRegistrationBinding
import com.hudhudit.catchapp.ui.main.MainActivity
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.ui.splash.SplashActivity
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatcherRegistrationFragment : BaseFragment() {

    private lateinit var binding: FragmentCatcherRegistrationBinding
    private lateinit var registrationActivity: RegistrationActivity
    private val viewModel by viewModels<CatcherRegistrationViewModel>()
    private lateinit var countries: MutableList<Country>
    var fullName = ""
    var countryId = ""
    var type = "signUp"
    var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatcherRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getToken()
        onClick()
        getCountries()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegistrationActivity) {
            registrationActivity = context
        }
    }

    private fun onClick(){
        binding.signUpBtn.setOnClickListener {
            type = "signUp"
            binding.signUpLayout.visibility = View.VISIBLE
            binding.signInLayout.visibility = View.GONE
            binding.signUp.setTextColor(resources.getColor(R.color.black))
            binding.signUpLine.visibility = View.VISIBLE
            binding.signIn.setTextColor(resources.getColor(R.color.light_gray))
            binding.signInLine.visibility = View.INVISIBLE
            binding.nextBtn.text = resources.getString(R.string.sign_up)
            binding.termConditionsLayout.visibility = View.VISIBLE
        }
        binding.signInBtn.setOnClickListener {
            type = "signIn"
            binding.signInLayout.visibility = View.VISIBLE
            binding.signUpLayout.visibility = View.GONE
            binding.signIn.setTextColor(resources.getColor(R.color.black))
            binding.signInLine.visibility = View.VISIBLE
            binding.signUp.setTextColor(resources.getColor(R.color.light_gray))
            binding.signUpLine.visibility = View.INVISIBLE
            binding.nextBtn.text = resources.getString(R.string.sign_in)
            binding.termConditionsLayout.visibility = View.GONE
        }
        binding.nextBtn.setOnClickListener { checkValidation() }
        binding.countriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                countryId = countries[i].id
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun getCountries(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCountries()
        viewModel.countriesStatus.observe(viewLifecycleOwner){
            when (it.status){
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    countries = it.data!!.results
                    setSpinner(it.data!!.results)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    //Toast.makeText(registrationActivity, "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setSpinner(countries: MutableList<Country>){
        val countryTitles: ArrayList<String> = ArrayList()
        for (country in countries){
            countryTitles.add(country.title)
        }
        val sortAdapter: ArrayAdapter<*> = ArrayAdapter(registrationActivity, R.layout.spinner_item, countryTitles)
        sortAdapter.setDropDownViewResource(R.layout.spinner_item)
        binding.countriesSpinner.adapter = sortAdapter
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String?> ->
                if (!task.isSuccessful) {
                    Log.w(
                        "FAILED",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@addOnCompleteListener
                }
                token = task.result!!
            }
    }

    private fun checkValidation(){
        if(type == "signUp"){
            fullName = binding.fullNameEdt.text.toString()
            val phoneNumber = binding.phoneEdt.text.toString()
            when {
                fullName.isEmpty() -> {
                    binding.fullNameEdt.error = resources.getString(R.string.empty_full_name)
                }
                phoneNumber.isEmpty() -> {
                    binding.phoneEdt.error = resources.getString(R.string.empty_phone_number)
                }
                phoneNumber.startsWith("0") -> {
                    phoneNumber.substring(1)
                    val fullPhoneNumber = binding.phoneCcp.selectedCountryCodeWithPlus+phoneNumber
                    checkPhone(fullPhoneNumber)
                }
                else -> {
                    val fullPhoneNumber = binding.phoneCcp.selectedCountryCodeWithPlus+phoneNumber
                    checkPhone(fullPhoneNumber)
                }
            }
        }else if (type == "signIn"){
            val phoneNumber = binding.signInPhoneEdt.text.toString()
            when {
                phoneNumber.isEmpty() -> {
                    binding.signInPhoneEdt.error = resources.getString(R.string.empty_phone_number)
                }
                phoneNumber.startsWith("0") -> {
                    phoneNumber.substring(1)
                }
                else -> {
                    val fullPhoneNumber = binding.signInPhoneCcp.selectedCountryCodeWithPlus+phoneNumber
                    checkPhone(fullPhoneNumber)
                }
            }
        }
    }

    private fun checkPhone(phoneNumber: String){
        binding.progressBar.visibility = View.VISIBLE
        val fullPhone = CheckPhone(phoneNumber)
        viewModel.checkPhone(fullPhone)
        viewModel.checkPhoneStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (type == "signUp"){
                        val catcherUser = CatcherUserSignUp(fullName, phoneNumber, countryId)
                        Log.d("countryId", countryId)
                        AppConstants.catcherSignUp = catcherUser
//                        AppConstants.catcherSignUp.country_id = countryId
                        findNavController().navigate(CatcherRegistrationFragmentDirections.actionCatcherRegistrationFragmentToCatcherVerificationFragment(type))
                    }else if (type == "signIn"){
                        Toast.makeText(registrationActivity, it.data!!.status.massage, Toast.LENGTH_SHORT).show()
                       // startActivity(Intent(requireContext(),MainActivity::class.java))
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    if (type == "signUp"){
                        Toast.makeText(registrationActivity, resources.getString(R.string.phone_existed), Toast.LENGTH_SHORT).show()
                    }else if (type == "signIn"){
                        val catcherUser = UserSignIn(phoneNumber, token)
                        AppConstants.signIn = catcherUser
                        findNavController().navigate(CatcherRegistrationFragmentDirections.actionCatcherRegistrationFragmentToCatcherVerificationFragment(type))
                    }
                }
            }
        }
    }

}