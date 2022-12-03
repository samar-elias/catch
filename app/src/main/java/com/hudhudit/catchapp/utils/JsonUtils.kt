package com.hudhudit.catchapp.utils

import com.google.gson.Gson
import com.google.gson.JsonObject


inline fun <reified T> ToDataModel(item: Any?): T {
    var gson = Gson()
    var jsonObject: JsonObject = gson.toJsonTree(item).asJsonObject;
    return gson.fromJson(jsonObject, T::class.java)
}