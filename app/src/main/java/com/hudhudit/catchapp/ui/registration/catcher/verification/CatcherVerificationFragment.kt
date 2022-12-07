package com.hudhudit.catchapp.ui.registration.catcher.verification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcherVerificationBinding
import com.hudhudit.catchapp.ui.main.MainActivity
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CatcherVerificationFragment : BaseFragment() {

    lateinit var binding: FragmentCatcherVerificationBinding
    private lateinit var registrationActivity: RegistrationActivity
    private val viewModel by viewModels<CatcherVerificationViewModel>()
    val args: CatcherVerificationFragmentArgs by navArgs()
    var code = ""
    var type = ""
    var phoneNum = ""
    var codeBySystem: String = ""
    var token = ""
    lateinit var mAuth: FirebaseAuth
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatcherVerificationBinding.inflate(layoutInflater)
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
        getToken()
        initViews()
        onClick()
    }

    private fun initViews(){
        type = args.type
        if (type == "signUp"){
            phoneNum = AppConstants.catcherSignUp.phone
        }else if (type == "signIn"){
            phoneNum = AppConstants.signIn.phone
        }
        mAuth = FirebaseAuth.getInstance()
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                codeBySystem = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    binding.verificationCodeEdt.setText(code)
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        sendVerificationCodeToUser(phoneNum)
    }

    private fun onClick(){
        binding.background.setOnClickListener { registrationActivity.hideKeyboard() }
        binding.navigateBack.setOnClickListener { findNavController().popBackStack() }
        binding.resendCode.setOnClickListener {
            Toast.makeText(registrationActivity, resources.getString(R.string.code_sent), Toast.LENGTH_SHORT).show()
            sendVerificationCodeToUser(phoneNum)
        }
        binding.nextBtn.setOnClickListener {
            if (binding.verificationCodeEdt.text.toString().isNotEmpty()){
                code = binding.verificationCodeEdt.text.toString()
                verifyCode(code)
            }
        }
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

    private fun verifyCode(code: String) {
        binding.progressBar.visibility = View.VISIBLE
        val credential = PhoneAuthProvider.getCredential(codeBySystem, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(
            registrationActivity
        ) { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
                Toast.makeText(context, resources.getString(R.string.verification_completed), Toast.LENGTH_SHORT).show()
                if (type == "signUp"){
                    findNavController().navigate(R.id.action_catcherVerificationFragment_to_catcherCarTypeFragment)
                }else if (type == "signIn"){
                    signIn()
                }
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, resources.getString(R.string.verification_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendVerificationCodeToUser(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(2L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(registrationActivity) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signIn(){
        binding.progressBar.visibility = View.VISIBLE
        AppConstants.signIn.fcm_token = token
        viewModel.signIn(AppConstants.signIn)
        viewModel.signInStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    AppConstants.userType = "1"
                    AppConstants.catcherUser = it.data!!
                    saveUserToSharedPreferences()
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
//                    Toast.makeText(registrationActivity, "Signed in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveUserToSharedPreferences() {
        val sharedPreferences =
            registrationActivity.getSharedPreferences(AppConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(AppConstants.ID_KEY, AppConstants.catcherUser.results.id)

        val gson = Gson()
        val json = gson.toJson(AppConstants.catcherUser)
        editor.putString(AppConstants.USER_KEY, json)
        editor.putString(AppConstants.TYPE_KEY, "1")
        editor.apply()
        val intent = Intent(registrationActivity, MainActivity:: class.java)
        startActivity(intent)
        registrationActivity.finish()
    }

}