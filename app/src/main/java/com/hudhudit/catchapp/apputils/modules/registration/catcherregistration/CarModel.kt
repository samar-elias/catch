package com.hudhudit.catchapp.apputils.modules.registration.catcherregistration

import com.hudhudit.catchapp.apputils.modules.Status

data class CarModel(val id: String,
                    val name: String)

data class CarModels(val status: Status,
                     val results: ArrayList<CarModel>)
