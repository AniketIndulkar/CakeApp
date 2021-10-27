package com.task.cakeapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.cakeapp.data.CakeModel

@Dao
interface CakeDao {

    //To query all the cakes
    @Query("SELECT * FROM Cake ORDER BY title")
    fun getCakes(): LiveData<List<CakeModel>>

    //to insert all the cakes we received from API
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cakes: List<CakeModel>) : List<Long>
}