package com.hudhudit.catchapp.apputils.modules.catchee.history

import com.hudhudit.catchapp.apputils.modules.Status

data class OrdersCount(val no_orders: String, val total_price: String)

data class Counts(val status: Status, val results: OrdersCount)
