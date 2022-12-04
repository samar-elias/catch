package com.hudhudit.catchapp.apputils.modules.registration.catcherregistration

import com.hudhudit.catchapp.apputils.modules.Status

data class CatcherUserResponse(val status: Status,
                               val token: String,
                               val results: CatcherUser
)
