package com.hudhudit.catchapp.ui.registration.catcher.cartype

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CarTypes
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CountryId
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcherCarTypeViewModel @Inject constructor(val registrationDataSource: RegistrationDataSource): ViewModel() {

    val carTypesStatus = MutableLiveData<Resource<CarTypes>>()

    fun getCarTypes(countryId: CountryId){
        viewModelScope.launch {
            val response = registrationDataSource.getCarTypes(countryId)
            carTypesStatus.postValue(response)
        }
    }

}