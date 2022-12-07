package com.hudhudit.catchapp.retrofit.repostore


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hudhudit.catchapp.apputils.modules.driverlocation.DriverModel
import com.hudhudit.catchapp.utils.AppConstants.Companion.USER
import com.hudhudit.catchapp.utils.Resource


class MapRepositoryImp(
    val database: FirebaseDatabase,


    ) : MapRepository {

    override suspend fun getDriver(result: (Resource<MutableList<DriverModel>>) -> Unit) {


        database.getReference(USER).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // userChat.clear()
                    var userlocation = mutableListOf<DriverModel>()
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val driverModel: DriverModel? =
                            dataSnapshot1.getValue(DriverModel::class.java)
                        userlocation.add(driverModel!!)
                    }

                    result.invoke(
                        Resource.success(userlocation)
                    )

                } else {
                    result.invoke(
                        Resource.error("no data", null)
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result.invoke(
                    Resource.error(
                        error.toString(), null
                    )
                )
            }

        })

    }
}