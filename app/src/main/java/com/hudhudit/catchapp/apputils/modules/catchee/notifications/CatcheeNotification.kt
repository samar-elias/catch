package com.hudhudit.catchapp.apputils.modules.catchee.notifications

data class CatcheeNotification(val id: String,
                               val driver_info: DriverInfo?,
                               val title: String,
                               val description: String,
                               val date: String,
                               val times: String,
                               val data_id: String,
                               val status: String)
