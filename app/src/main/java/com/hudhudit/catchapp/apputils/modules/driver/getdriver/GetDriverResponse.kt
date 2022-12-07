package com.hudhudit.catchapp.apputils.modules.driver.getdriver

import com.hudhudit.catchapp.apputils.modules.Status

data class GetDriverResponse(
    val results: List<GetDriverModel>,
    val status: Status
)