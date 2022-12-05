package com.hudhudit.catchapp.apputils.modules.registration.catcherregistration

import com.hudhudit.catchapp.apputils.modules.Status

data class CarType(val id: String,
                   val title: String,
                   val icons: String)

data class CarTypes(val status: Status,
                    val results: ArrayList<CarType?>)

data class CountryId(val country_id: String)
