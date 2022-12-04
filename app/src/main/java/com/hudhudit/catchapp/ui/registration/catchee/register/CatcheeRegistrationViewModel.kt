package com.hudhudit.catchapp.ui.registration.catchee.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserPost
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CatcheeUserResponse
import com.hudhudit.catchapp.apputils.modules.catcheeregistration.CheckPhone
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcheeRegistrationViewModel @Inject constructor(val registrationDataSource: RegistrationDataSource): ViewModel() {

    val createAccountStatus = MutableLiveData<Resource<CatcheeUserResponse>>()
    val checkPhoneStatus = MutableLiveData<Resource<BooleanResponse>>()

    fun createAccount(catcheeUserPost: CatcheeUserPost){
        viewModelScope.launch {
            val response = registrationDataSource.catcheeCreateAccount(catcheeUserPost)
            createAccountStatus.postValue(response)
        }
    }

    fun checkPhone(checkPhone: CheckPhone){
        viewModelScope.launch {
            val response = registrationDataSource.checkPhone(checkPhone)
            checkPhoneStatus.postValue(response)
        }
    }
}