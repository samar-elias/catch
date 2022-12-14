package com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatcheeUserSignUp(var name: String,
                             var phone: String,
                             var country_id: String,
                             var plan_id: String,
                             var fcm_token: String): Parcelable
