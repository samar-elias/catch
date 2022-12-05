package com.hudhudit.catchapp.ui.registration.catcher.carinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.*
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarInformationViewModel @Inject constructor(val registrationDataSource: RegistrationDataSource): ViewModel() {

    val carBrandsStatus = MutableLiveData<Resource<CarBrands>>()
    val carModelsStatus = MutableLiveData<Resource<CarModels>>()
    val createAccountStatus = MutableLiveData<Resource<CatcherUserResponse>>()

    fun getCarBrands(){
        viewModelScope.launch {
            val response = registrationDataSource.getCarBrands()
            carBrandsStatus.postValue(response)
        }
    }

    fun getCarModels(id: String){
        viewModelScope.launch {
            val response = registrationDataSource.getCarModels(id)
            carModelsStatus.postValue(response)
        }
    }

    fun createAccount(catcherUserSignUp: CatcherUserSignUp){
        viewModelScope.launch {
            val response = registrationDataSource.catcherCreateAccount(catcherUserSignUp)
            createAccountStatus.postValue(response)
        }
    }

}