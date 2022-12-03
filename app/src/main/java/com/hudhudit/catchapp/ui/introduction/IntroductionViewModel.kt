package com.hudhudit.catchapp.ui.introduction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.introduction.IntroData
import com.hudhudit.catchapp.retrofit.data.RegistrationDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor( val registrationDataSource: RegistrationDataSource): ViewModel() {

    val introStatus = MutableLiveData<Resource<IntroData>>()

    fun getIntroData(){
        viewModelScope.launch {
            val response = registrationDataSource.getIntro()
            introStatus.postValue(response)
        }
    }
}