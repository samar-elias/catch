package com.hudhudit.catchapp.apputils.modules.driver.endorder

data class EndOrderRequest(
    val distance: String,
    val dropoff_latitude: String,
    val dropoff_longitude: String,
    val orders_id: String
)