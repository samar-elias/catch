package com.hudhudit.catchapp.apputils.modules.registration

import com.hudhudit.catchapp.apputils.modules.Status

data class Country(val id: String, val title: String)

data class Countries(val status: Status, val results: ArrayList<Country>)
