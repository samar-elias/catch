package com.hudhudit.catchapp.apputils.modules.catcher.history

import com.hudhudit.catchapp.apputils.modules.Status

data class CatcherOrdersHistory(val status: Status,
                                val results: ArrayList<CatcherOrderHistory>)
