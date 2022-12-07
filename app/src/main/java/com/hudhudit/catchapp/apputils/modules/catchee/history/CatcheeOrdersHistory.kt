package com.hudhudit.catchapp.apputils.modules.catchee.history

import com.hudhudit.catchapp.apputils.modules.Status

data class CatcheeOrdersHistory(val status: Status,
                                val results: ArrayList<CatcheeOrderHistory>)
