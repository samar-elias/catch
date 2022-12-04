package com.hudhudit.catchapp.apputils.modules.catcheeregistration

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatcheeUserPost(val name: String,
                           val phone: String,
                           val plan_id: String,
                           val fcm_token: String): Parcelable
