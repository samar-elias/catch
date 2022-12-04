package com.hudhudit.catchapp.ui.registration.catchee.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserPost
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CheckPhone
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcheeRegistrationBinding
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatcheeRegistrationFragment : BaseFragment() {

    private lateinit var binding: FragmentCatcheeRegistrationBinding
    private lateinit var registrationActivity: RegistrationActivity
    private val viewModel by viewModels<CatcheeRegistrationViewModel>()
    var type = "signUp"
    var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatcheeRegistrationBinding.inflate(layoutInflater)
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
        FirebaseApp.initializeApp(registrationActivity)
        onClick()
        getToken()
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
        }
        binding.signInBtn.setOnClickListener {
            type = "signIn"
            binding.signInLayout.visibility = View.VISIBLE
            binding.signUpLayout.visibility = View.GONE
            binding.signIn.setTextColor(resources.getColor(R.color.black))
            binding.signInLine.visibility = View.VISIBLE
            binding.signUp.setTextColor(resources.getColor(R.color.light_gray))
            binding.signUpLine.visibility = View.INVISIBLE
        }
        binding.nextBtn.setOnClickListener { checkValidation() }
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
            val fullName = binding.fullNameEdt.text.toString()
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
                }
                else -> {
                    val fullPhoneNumber = binding.phoneCcp.selectedCountryCodeWithPlus+phoneNumber
                    checkPhone(fullName, fullPhoneNumber, token)
                }
            }
        }
    }

    private fun checkPhone(fullName: String, phoneNumber: String, token: String){
        binding.progressBar.visibility = View.VISIBLE
        val fullPhone = CheckPhone(phoneNumber)
        viewModel.checkPhone(fullPhone)
        viewModel.checkPhoneStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val catcheeUser = CatcheeUserPost(fullName, phoneNumber, "0", token)
                    AppConstants.catcheeSignUp = catcheeUser
                    findNavController().navigate(CatcheeRegistrationFragmentDirections.actionCatcheeRegistrationFragmentToCatcheeVerificationFragment(type))
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(registrationActivity, resources.getString(R.string.phone_existed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}