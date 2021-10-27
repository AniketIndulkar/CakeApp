package com.task.cakeapp.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.cakeapp.data.CakeModel
import com.task.cakeapp.data.CakeRepository
import com.task.cakeapp.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel internal constructor(private val cakeRepository: CakeRepository, val context: Context) : ViewModel(){

    //Scope for coroutines
    private val myScope = CoroutineScope(Dispatchers.IO)

    //Live data for all the cake data
    val allCakes : LiveData<List<CakeModel>> = cakeRepository.cakes

    //Live data to keep track of Loading state
    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading : LiveData<Boolean>
            get() = _isLoading

    //Live data to check Internet connectivity
    private var _isConnected = MutableLiveData<Boolean>()

    val isConnected : LiveData<Boolean>
        get() = _isConnected

    //Live data to emmit what is the data source
    // 1 - No data available
    //2 - Data from API
    //3 - Data from Local Database without internet connectivity
    private var _dataSource = MutableLiveData<Int>()

    val dataSource : LiveData<Int>
        get() = _dataSource

    init {
        _isLoading.value=false
        _dataSource.value=2
    }

    fun getAllCakesFromAPI(){
        cakeRepository.getAllCakes()
    }

    //Setting connect and data source live data values based on conditions
     fun checkDataAvailability(){
        if (!Util.isOnline(context) && allCakes.value==null){
            _isConnected.value= false
            _dataSource.value=1// No data Available
        }else if(!Util.isOnline(context) && (allCakes.value!=null && allCakes.value!!.isNotEmpty())){
            _isConnected.value=false
            _dataSource.value=3// Data Previously stored in Local dB
        }else if (Util.isOnline(context)&& (allCakes.value!=null && allCakes.value!!.isNotEmpty())){
            _isConnected.value=true
            _dataSource.value=2// Data From API
        }
    }


    //click handler for Refresh cake list
    fun onClickRefresh(){
        if (Util.isOnline(context)){
            _isLoading.value= true
            _isConnected.value=true
            myScope.launch {
                withContext(Dispatchers.Default){// Background thread
                    if (_isConnected.value!!){
                        cakeRepository.fetchAllCakes()
                    }
                    withContext(Dispatchers.Main){
                        _isLoading.value = false
                    }
                }
            }
        }else{
            _isConnected.value=false
            //Toast to notify user that there is no internet connectivity
            Toast.makeText(context,"No internet connected", Toast.LENGTH_LONG).show()
        }

        checkDataAvailability()// To check the state and set Visibility of Views using data binding

    }

}


//Custom ViewModel factory to get Repository class and context in MainViewModel
class MainViewModelFactory(
    private val repository: CakeRepository,
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(repository,context) as T
}