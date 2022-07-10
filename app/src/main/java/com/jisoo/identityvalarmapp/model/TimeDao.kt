//package com.jisoo.identityvalarmapp.model
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//
//@Dao
//interface TimeDao {
//
//    @Insert
//    suspend fun insert(time: Time)
//
//    @Update
//    suspend fun update(time: Time)
//
//    @Delete
//    suspend fun delete(time: Time)
//
//    @Query("SELECT * FROM TIME")
//    fun getAllTimeData(): LiveData<List<Time>>
//
//}