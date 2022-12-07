package com.hudhudit.catchapp.apputils.modules.driver.order

data class GetOrderModel(
    val client_name: String,
    val client_phone: String,
    val date: String,
    val id: String,
    val time: String
)