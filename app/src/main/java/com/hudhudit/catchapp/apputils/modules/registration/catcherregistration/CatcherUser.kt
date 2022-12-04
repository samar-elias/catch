package com.hudhudit.catchapp.apputils.modules.registration.catcherregistration

data class CatcherUser(val id: String,
                       val name: String,
                       val phone: String,
                       val cart_type: String,
                       val plate_number: String,
                       val car_brand_id: String,
                       val car_model_id: String,
                       val car_image_id: String,
                       val latitude: String,
                       val longitude: String,
                       val fcm_token: String,
                       val status: String)
