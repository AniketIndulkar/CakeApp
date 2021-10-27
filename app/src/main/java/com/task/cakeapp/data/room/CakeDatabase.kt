package com.task.cakeapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.cakeapp.data.CakeModel

//Room Database class
@Database(entities = [CakeModel::class], version = 1, exportSchema = false)
abstract class CakeDatabase : RoomDatabase() {


    abstract fun cakeDao(): CakeDao // Abstract method to get CakeDao

    companion object{
        // For Singleton instantiation
        @Volatile private var instance: CakeDatabase? = null
        @Volatile private var DB_NAME: String = "CakeDatabase"

        //To create singalton instance of Room Database
        fun getInstance(context : Context) : CakeDatabase {
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }

        }

        //Building Room Database object
        private fun buildDatabase(context: Context): CakeDatabase {
            return Room.databaseBuilder(context, CakeDatabase::class.java, DB_NAME).build()
        }
    }
}