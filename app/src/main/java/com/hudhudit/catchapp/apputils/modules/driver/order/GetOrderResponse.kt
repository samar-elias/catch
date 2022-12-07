package com.hudhudit.catchapp.apputils.modules.driver.order

import com.hudhudit.catchapp.utils.Resource

data class GetOrderResponse(
    val results: List<GetOrderModel>,
    val status: Resource.Status
)