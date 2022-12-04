package com.hudhudit.catchapp.ui.registration.catchee.verification

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.messaging.FirebaseMessaging
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcheeVerificationBinding
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CatcheeVerificationFragment : BaseFragment() {

    private lateinit var binding: FragmentCatcheeVerificationBinding
    private lateinit var registrationActivity: RegistrationActivity
    private val viewModel by viewModels<CatcheeVerificationViewModel>()
    val args: CatcheeVerificationFragmentArgs by navArgs()
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
        binding = FragmentCatcheeVerificationBinding.inflate(layoutInflater)
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
            phoneNum = AppConstants.catcheeSignUp.phone
        }else if (type == "signIn"){
            phoneNum = AppConstants.catcheeSignIn.phone
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
                    createAccount()
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

    private fun createAccount(){
        binding.progressBar.visibility = View.VISIBLE
        AppConstants.catcheeSignUp.fcm_token = token
        viewModel.createAccount(AppConstants.catcheeSignUp)
        viewModel.createAccountStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(registrationActivity, "Signed up user id: "+it.data!!.results.id, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(registrationActivity, "Signed up failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signIn(){
        binding.progressBar.visibility = View.VISIBLE
        AppConstants.catcheeSignIn.fcm_token = token
        viewModel.signIn(AppConstants.catcheeSignIn)
        viewModel.signInStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(registrationActivity, "Signed in user id: "+it.data!!.results.id, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(registrationActivity, "Signed in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}