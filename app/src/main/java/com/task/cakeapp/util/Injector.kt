package com.task.cakeapp.util

import android.content.Context
import com.task.cakeapp.data.CakeRepository
import com.task.cakeapp.data.api.NetworkServices
import com.task.cakeapp.data.room.CakeDatabase
import com.task.cakeapp.ui.MainViewModelFactory

//Custom ViewModel fatory interface
interface ViewModelFactoryProvider{
    fun provideMainViewModelFactory(context: Context) : MainViewModelFactory
}

//Inject object which provides Custom ViewModel
val Injector: ViewModelFactoryProvider
    get() = currentInjector

private object DefaultViewModelProvider: ViewModelFactoryProvider {
    //Function to create Repository class instance
    private fun getCakeRepository(context: Context): CakeRepository {
        return CakeRepository.getInstance(
            cakeDao(context),
            cakeService(),
            context
        )
    }

    //To create Retrofit services class instance
    private fun cakeService() = NetworkServices()

    //Getting CakeDao from CakeDatabase
    private fun cakeDao(context: Context) =
        CakeDatabase.getInstance(context.applicationContext).cakeDao()

    //Providing Custom ViewModel
    override fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getCakeRepository(context)
        return MainViewModelFactory(repository,context)    }
}

// Object of ViewModel factory ptovider
@Volatile private var currentInjector: ViewModelFactoryProvider =
    DefaultViewModelProvider

