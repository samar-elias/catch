package com.hudhudit.catchapp.ui.main.catcher.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.catchee.notifications.CatcheeNotifications
import com.hudhudit.catchapp.apputils.modules.catcher.notifications.CatcherNotifications
import com.hudhudit.catchapp.retrofit.data.MainDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcherNotificationsViewModel @Inject constructor(val mainDataSource: MainDataSource): ViewModel() {

    val notificationsStatus = MutableLiveData<Resource<CatcherNotifications>>()

    fun getNotifications(token: String, page: String){
        viewModelScope.launch {
            val response = mainDataSource.getCatcherNotifications(token, page)
            notificationsStatus.postValue(response)
        }
    }

}