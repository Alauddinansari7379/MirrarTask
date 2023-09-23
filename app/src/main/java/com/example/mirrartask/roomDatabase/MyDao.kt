package com.example.mirrartask.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyDao {

    @Query("SELECT * FROM my_table ORDER BY id ASC")
    fun readData(): LiveData<List<Table>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: Table)

    @Query("DELETE FROM my_table")
    fun delete()

}