package com.hudhudit.artook.apputils.remote.di


import com.google.firebase.database.FirebaseDatabase
import com.hudhudit.catchapp.retrofit.repostore.MapRepositoryImp
import com.hudhudit.catchapp.retrofit.repostore.MapRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        database: FirebaseDatabase,


    ): MapRepository {
        return MapRepositoryImp(database)
    }



}