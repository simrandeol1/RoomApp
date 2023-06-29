package com.example.roomapp.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomapp.data.Data

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(data: Data?)

    @Query("SELECT * FROM data")
    suspend fun getAllDataSet(): Data

}