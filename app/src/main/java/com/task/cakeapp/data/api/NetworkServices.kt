package com.task.cakeapp.data.api

import com.task.cakeapp.data.CakeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServices {

    //Building Retrofit object with Gson converter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //Creating services object from Retrofit instance
    private val cakeServices = retrofit.create(CakeServices::class.java)

    //Only API call needed to function this application
    suspend fun getAllCakes() : List<CakeModel> = withContext(Dispatchers.Default){
        val results = cakeServices.getAllCakes()
        results.sortedBy { it.title }//Sorting based on title. There was no need to put id param in data model so i skipped it
    }

}