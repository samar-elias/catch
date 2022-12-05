package com.hudhudit.catchapp.apputils.modules.registration.catcherregistration

data class CatcherUserSignUp(var name: String = "",
                             var phone: String = "",
                             var country_id: String = "",
                             var cart_type: String = "",
                             var plate_number: String = "",
                             var car_brand_id: String = "",
                             var car_model_id: String = "",
                             var car_image_front: String = "",
                             var car_image_back: String = "",
                             var latitude: String = "",
                             var longitude: String = "",
                             var plan_id: String = "",
                             var fcm_token: String = "")
