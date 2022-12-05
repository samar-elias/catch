package com.hudhudit.catchapp.ui.registration.catcher.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.registration.Countries
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcherRegistrationViewModel @Inject constructor(val registrationDataSource: RegistrationDataSource): ViewModel() {

    val checkPhoneStatus = MutableLiveData<Resource<BooleanResponse>>()
    val countriesStatus = MutableLiveData<Resource<Countries>>()

    fun checkPhone(checkPhone: CheckPhone){
        viewModelScope.launch {
            val response = registrationDataSource.checkCatcherPhone(checkPhone)
            checkPhoneStatus.postValue(response)
        }
    }

    fun getCountries(){
        viewModelScope.launch {
            val response = registrationDataSource.getCountries()
            countriesStatus.postValue(response)
        }
    }
}