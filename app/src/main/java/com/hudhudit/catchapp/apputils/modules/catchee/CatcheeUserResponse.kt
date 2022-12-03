package com.hudhudit.catchapp.apputils.modules.catchee

import com.hudhudit.catchapp.apputils.modules.Status

data class CatcheeUserResponse (val status: Status,
                                val token: String,
                                val results: CatcheeUser)

