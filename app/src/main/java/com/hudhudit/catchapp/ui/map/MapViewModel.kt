package com.hudhudit.catchapp.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.driverlocation.DriverModel
import com.hudhudit.catchapp.retrofit.repostore.MapRepository
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel  @Inject constructor(
    val repository: MapRepository

) : ViewModel()  {
    val getDriverStatus = MutableLiveData<Resource<MutableList<DriverModel>>?>()

    fun getDriver() {
        viewModelScope.launch {
            repository.getDriver  { getDriverStatus.postValue(it)}

        }
    }
    fun reset() {
        getDriverStatus.postValue(null)
    }

}