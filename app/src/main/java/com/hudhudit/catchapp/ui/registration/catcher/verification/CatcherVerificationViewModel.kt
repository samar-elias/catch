package com.hudhudit.catchapp.ui.registration.catcher.verification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.driver.adddrive.DriverUserModel
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUserResponse
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.retrofit.repostore.MapRepository
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcherVerificationViewModel @Inject constructor(val registrationDataSource: RegistrationDataSource,val repository: MapRepository): ViewModel() {

    val signInStatus = MutableLiveData<Resource<CatcherUserResponse>>()
    val addDriverStatus = MutableLiveData<Resource<Pair<DriverUserModel,String>>>()


    fun addDriver(note: DriverUserModel){
        viewModelScope.launch {
            repository.addDriver(note) { addDriverStatus.value = it }

        }

    }
    fun signIn(catcherUserSignIn: UserSignIn){
        viewModelScope.launch {
            val response = registrationDataSource.catcherLogin(catcherUserSignIn)
            signInStatus.postValue(response)
        }
    }
}