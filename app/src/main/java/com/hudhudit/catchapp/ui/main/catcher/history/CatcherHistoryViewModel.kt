package com.hudhudit.catchapp.ui.main.catcher.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.catchee.history.CatcheeOrdersHistory
import com.hudhudit.catchapp.apputils.modules.catchee.history.Counts
import com.hudhudit.catchapp.apputils.modules.catcher.history.CatcherOrdersHistory
import com.hudhudit.catchapp.retrofit.data.MainDataSource
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatcherHistoryViewModel @Inject constructor(val mainDataSource: MainDataSource): ViewModel() {

    val ordersHistoryStatus = MutableLiveData<Resource<CatcherOrdersHistory>>()
    val historyDataStatus = MutableLiveData<Resource<Counts>>()

    fun getOrderHistory(token: String, page: String){
        viewModelScope.launch {
            val response = mainDataSource.getCatcherHistory(token, page)
            ordersHistoryStatus.postValue(response)
        }
    }

    fun getHistoryData(token: String){
        viewModelScope.launch {
            val response = mainDataSource.getCatcherHistoryData(token)
            historyDataStatus.postValue(response)
        }
    }

}