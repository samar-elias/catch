package com.hudhudit.catchapp.apputils.modules.registration.catcherregistration

import com.hudhudit.catchapp.apputils.modules.Status

data class CarBrand (val id: String,
                     val name: String)

data class CarBrands(val status: Status,
                    val results: ArrayList<CarBrand>)