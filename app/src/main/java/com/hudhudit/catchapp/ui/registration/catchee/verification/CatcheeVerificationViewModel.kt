package com.hudhudit.catchapp.ui.registration.catchee.verification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcheeVerificationViewModel @Inject constructor(val registrationDataSource: RegistrationDataSource): ViewModel(){

    val createAccountStatus = MutableLiveData<Resource<CatcheeUserResponse>>()
    val signInStatus = MutableLiveData<Resource<CatcheeUserResponse>>()

    fun createAccount(catcheeUserSignUp: CatcheeUserSignUp){
        viewModelScope.launch {
            val response = registrationDataSource.catcheeCreateAccount(catcheeUserSignUp)
            createAccountStatus.postValue(response)
        }
    }

    fun signIn(catcheeUserSignIn: UserSignIn){
        viewModelScope.launch {
            val response = registrationDataSource.catcheeLogin(catcheeUserSignIn)
            signInStatus.postValue(response)
        }
    }
}