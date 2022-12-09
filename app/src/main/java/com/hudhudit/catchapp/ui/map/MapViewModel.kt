package com.hudhudit.catchapp.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hudhudit.catchapp.apputils.modules.BooleanResponse
import com.hudhudit.catchapp.apputils.modules.catcher.ChacherUserRequest
import com.hudhudit.catchapp.apputils.modules.driver.adddrive.DriverUserModel
import com.hudhudit.catchapp.apputils.modules.driver.getdriver.GetDriverResponse
import com.hudhudit.catchapp.apputils.modules.driver.order.CreatOrderRequest
import com.hudhudit.catchapp.apputils.modules.driver.order.OrderRequest
import com.hudhudit.catchapp.retrofit.data.MapDataSource
import com.hudhudit.catchapp.retrofit.repostore.MapRepository
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel  @Inject constructor(
    val repository: MapRepository,
    val mapDataSource: MapDataSource

) : ViewModel()  {
    val getDriverStatus = MutableLiveData<Resource<MutableList<DriverUserModel>>?>()
    val addOrderStatus = MutableLiveData<Resource<Pair<ChacherUserRequest,String>>>()
    val createOrdersStatus = MutableLiveData<Resource<BooleanResponse>>()
    val deleteOrderStatus = MutableLiveData<Resource<String?>?>()
    val cancelClientOrdersApiStatus = MutableLiveData<Resource<BooleanResponse>>()
    val updateAvailableStatus = MutableLiveData<Resource<Pair<DriverUserModel,String>>?>()
    val getAllRequestStatus = MutableLiveData<Resource<MutableList<ChacherUserRequest>>?>()

     fun updateDriverAvailable(

         driverUserModel: DriverUserModel)
     {
         viewModelScope.launch {
             repository.updateDriverAvailable(driverUserModel) {
                 updateAvailableStatus.postValue(it)
             }
         }
    }


    fun cancelClientOrdersApi(token: String, orderRequest: OrderRequest) {
        viewModelScope.launch {
            val response = mapDataSource.cancelClientOrders(token, orderRequest)
            createOrdersStatus.postValue(response)
        }
    }
    fun deleteOrderRequest(
        orderId:String
    ) {
        viewModelScope.launch {
            repository.deleteRequest(orderId) {
                deleteOrderStatus.postValue(it)
            }
        }
    }
    fun createOrders(token: String, creatOrderRequest: CreatOrderRequest) {
        viewModelScope.launch {
            val response = mapDataSource.createOrders(token, creatOrderRequest)
            createOrdersStatus.postValue(response)
        }
    }

    fun addOrderRequest(chacherUserRequest: ChacherUserRequest){
        viewModelScope.launch {
            repository.addOrderRequest(chacherUserRequest) { addOrderStatus.value = it }

        }

    }

    fun getDriver() {
        viewModelScope.launch {
            repository.getDriver  { getDriverStatus.postValue(it)}

        }
    }
    fun getAllRequest() {
        viewModelScope.launch {
            repository.getAllRequest  { getAllRequestStatus.postValue(it)}

        }
    }
    fun reset() {
        getDriverStatus.postValue(null)
    }
    fun updateAvailableReset(){
        updateAvailableStatus.postValue(null)
    }
    fun createOrderReset(){
        updateAvailableStatus.postValue(null)
    }

}