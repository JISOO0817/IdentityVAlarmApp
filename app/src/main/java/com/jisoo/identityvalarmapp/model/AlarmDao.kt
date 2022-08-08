package com.jisoo.identityvalarmapp.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AlarmDao {

    /**
     * 앱이 데이터베이스의 데이터를 쿼리, 업데이트, 삽입, 삭제하는 데 사용할 수 있는
     * 메서드를 제공함
     * */

    @Insert
    suspend fun insert(info: CharacInfo)

    @Update
    suspend fun update(info: CharacInfo)

    @Delete
    suspend fun delete(info: CharacInfo)

    @Query("SELECT * FROM CharacInfo ORDER BY BIRTH ASC")
    fun getAllData() : List<CharacInfo>

    @Query("SELECT * FROM CharacInfo ORDER BY BIRTH ASC")
    fun getAllDataList() : LiveData<List<CharacInfo>>

}