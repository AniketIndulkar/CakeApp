package com.task.cakeapp.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.task.cakeapp.data.api.NetworkServices
import com.task.cakeapp.data.room.CakeDao
import com.task.cakeapp.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


//Repository class is only point where data can be accessed and altered
class CakeRepository private constructor(
    private val cakeDao: CakeDao,
    private val cakeServices: NetworkServices,
    private val context: Context
) {


    //Method to call the suspend function which are calling the Cake API
    fun getAllCakes(){
        runBlocking {
            withContext(Dispatchers.Default) {// Background operations
                if (Util.isOnline(context))// Internet check
                    fetchAllCakes()
                return@withContext null
            }
        }
    }

    //Live data object of all the cakes
    val cakes: LiveData<List<CakeModel>> = liveData<List<CakeModel>> {
        val cakeLiveData = cakeDao.getCakes()
        emitSource(cakeLiveData)
    }

    //Function to call the API and put results in Database
    suspend fun fetchAllCakes() {
        val plants = cakeServices.getAllCakes()
        cakeDao.insertAll(plants)
    }

    //To get only one instance of CakeRepository
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: CakeRepository? = null

        fun getInstance(cakeDao: CakeDao, cakeServices: NetworkServices, context: Context) =
            instance ?: synchronized(this) {
                instance ?: CakeRepository(cakeDao, cakeServices, context).also { instance = it }
            }
    }
}