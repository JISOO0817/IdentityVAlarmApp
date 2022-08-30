package com.jisoo.identityvalarmapp.di

import android.content.Context
import com.jisoo.identityvalarmapp.model.AlarmDao
import com.jisoo.identityvalarmapp.model.AlarmDatabase
import com.jisoo.identityvalarmapp.model.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getDatabaseInstance(@ApplicationContext context: Context): AlarmDatabase {
        return AlarmDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun getAlarmDao(database: AlarmDatabase): AlarmDao {
        return database.alarmDao()
    }

//    @Singleton
//    @Provides
//    fun getRepository(repository: AlarmRepository): AlarmRepository {
//        return repository
//    }

}