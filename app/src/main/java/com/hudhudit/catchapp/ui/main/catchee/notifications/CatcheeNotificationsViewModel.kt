package com.hudhudit.catchapp.ui.main.catchee.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.catchee.notifications.CatcheeNotifications
import com.hudhudit.catchapp.retrofit.data.CatcheeDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcheeNotificationsViewModel @Inject constructor(val catcheeDataSource: CatcheeDataSource): ViewModel() {

    val notificationsStatus = MutableLiveData<Resource<CatcheeNotifications>>()

    fun getNotifications(token: String, page: String){
        viewModelScope.launch {
            val response = catcheeDataSource.getCatcheeNotifications(token, page)
            notificationsStatus.postValue(response)
        }
    }

}