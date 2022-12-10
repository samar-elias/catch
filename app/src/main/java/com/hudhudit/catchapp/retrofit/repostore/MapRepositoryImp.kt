package com.hudhudit.catchapp.retrofit.repostore


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hudhudit.catchapp.apputils.modules.catcher.ChacherUserRequest
import com.hudhudit.catchapp.apputils.modules.driver.adddrive.DriverUserModel
import com.hudhudit.catchapp.utils.AppConstants.Companion.DRIVER
import com.hudhudit.catchapp.utils.AppConstants.Companion.REQUESTUser
import com.hudhudit.catchapp.utils.Resource


class MapRepositoryImp(
    private val database: FirebaseDatabase,


    ) : MapRepository {

    override suspend fun getDriver(result: (Resource<MutableList<DriverUserModel>>) -> Unit) {


        database.getReference(DRIVER).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // userChat.clear()
                    var userlocation = mutableListOf<DriverUserModel>()
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val driverUserModel: DriverUserModel? =
                            dataSnapshot1.getValue(DriverUserModel::class.java)
                        userlocation.add(driverUserModel!!)
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
    override suspend fun addDriver(
        driverUserModel: DriverUserModel,
        result: (Resource<Pair<DriverUserModel, String>>) -> Unit,
    ) {
        val keyDatabaseReference = database.getReference(DRIVER).push()
        val key = keyDatabaseReference.key
        driverUserModel.id = key!!
        database.getReference(DRIVER).child(key).setValue(driverUserModel).addOnSuccessListener {
            result.invoke(
                Resource.success(Pair(driverUserModel, "driver has been created successfully"))
            )

        }.addOnFailureListener {
            result.invoke(
                Resource.error(
                    it.localizedMessage
                )
            )
        }


    }

    override suspend fun addOrderRequest(
        chacherUserRequest: ChacherUserRequest,
        result: (Resource<Pair<ChacherUserRequest, String>>) -> Unit
    ) {
        val keyDatabaseReference = database.getReference(REQUESTUser).push()
        val key = keyDatabaseReference.key
        chacherUserRequest.id = key!!
        database.getReference(REQUESTUser).child(key).setValue(chacherUserRequest).addOnSuccessListener {
            result.invoke(
                Resource.success(Pair(chacherUserRequest, "order has been created successfully"))
            )

        }.addOnFailureListener {
            result.invoke(
                Resource.error(
                    it.localizedMessage
                )
            )
        }


    }

    override suspend fun deleteRequest(orderId: String, result: (Resource<String>) -> Unit) {
        database.getReference(REQUESTUser).child(orderId).removeValue().addOnSuccessListener {
            result.invoke(
                Resource.success("remove successfully")
            )

        }.addOnFailureListener {
            result.invoke(
                Resource.error(
                    it.localizedMessage
                )
            )
        }
    }

    override suspend fun getAllRequest(result: (Resource<MutableList<ChacherUserRequest>>) -> Unit) {
        database.getReference(REQUESTUser).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // userChat.clear()
                    var userlocation = mutableListOf<ChacherUserRequest>()
                    for (dataSnapshot1 in dataSnapshot.children) {
                        val chacherUserRequest: ChacherUserRequest? =
                            dataSnapshot1.getValue(ChacherUserRequest::class.java)
                        userlocation.add(chacherUserRequest!!)
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

    override suspend fun updateOrderStatus(
        status:String,
        chacherUserRequest: ChacherUserRequest,
        result: (Resource<Pair<ChacherUserRequest, String>>) -> Unit
    ) {
        val map = HashMap<String, Any>()

        map["statusOrder"] = status

        database.getReference(REQUESTUser).child(chacherUserRequest.id!!).updateChildren(map).addOnSuccessListener {
            result.invoke(
                Resource.success(Pair(chacherUserRequest, "update successfully"))
            )

        }.addOnFailureListener {
            result.invoke(
                Resource.error(
                    it.localizedMessage
                )
            )
        }

    }

    override suspend fun updateDriverAvailable(
        driverUserModel: DriverUserModel,
        result: (Resource<Pair<DriverUserModel, String>>) -> Unit
    ) {

        val map = HashMap<String, Any>()

        if ( driverUserModel.avsilable == "true"){
            map["avsilable"] = "false"
        }else{
            map["avsilable"] = "true"
        }

        database.getReference(DRIVER).child(driverUserModel.id).updateChildren(map).addOnSuccessListener {
            result.invoke(
                Resource.success(Pair(driverUserModel, "update successfully"))
            )

        }.addOnFailureListener {
            result.invoke(
                Resource.error(
                    it.localizedMessage
                )
            )
        }
    }


}