package com.hudhudit.catchapp.utils

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }
fun <A> performRemoteOperation(networkCall: suspend () -> Resource<A>,): LiveData<Resource<A>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(responseStatus)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }
fun <T> performRemoteOperation2( databaseQuery:  () -> LiveData<T>) : LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }

fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch { work() }

fun encoder(imagePath: String?): String? {
    var base64Image = ""
    val file = File(imagePath)
    try {
        FileInputStream(file).use { imageInFile ->
            // Reading a Image file from file system
            val imageData = ByteArray(file.length().toInt())
            imageInFile.read(imageData)
            base64Image = Base64.encodeToString(imageData, 0)
        }
    } catch (e: FileNotFoundException) {
        println("Image not found$e")
    } catch (ioe: IOException) {
        println("Exception while reading the Image $ioe")
    }
    return base64Image
}
