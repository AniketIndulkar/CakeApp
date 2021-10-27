package com.task.cakeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.task.cakeapp.databinding.ActivityMainBinding

import com.task.cakeapp.data.CakeModel
import com.task.cakeapp.util.Injector


class MainActivity : AppCompatActivity(), OnCakeClickListener {

    //Getting ViewModel from injector
    private val mainViewModel: MainViewModel by viewModels {
        Injector.provideMainViewModelFactory(this)
    }

     private var cakeAdapter : CakeListAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Data binding
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        //Calling API to get All the data
        mainViewModel.getAllCakesFromAPI()

        //Setting data and lyfecycle to binding
        binding.lifecycleOwner= this
        binding.activityViewModel = mainViewModel


        //Observing the data we got from API
        mainViewModel.allCakes.observe(this, {
            if (it.isNotEmpty()){
                mainViewModel.checkDataAvailability()
                if (cakeAdapter== null){
                    cakeAdapter = CakeListAdapter()
                    cakeAdapter!!.setClickListener(this@MainActivity)
                    binding.cakeList.adapter = cakeAdapter
                }
                cakeAdapter!!.submitList(it)
                cakeAdapter!!.notifyDataSetChanged()
            }
        })

        //Observing the datasource live data
        //To set Text bottom textview
        mainViewModel.dataSource.observe(this,{
            var dataSource = ""
            dataSource = when (it) {
                2 -> {
                    "Data loaded from Cake API"
                }
                3 -> {
                    "Data loaded from local Database"
                }
                else -> {
                    "No data available"
                }
            }

            binding.txtDataSource.text=dataSource

        })
    }

    //click listener of Recyclerview
    override fun onCakeClick(cake: CakeModel) {
        //Calling Dialog for image click
        CakeDialog(cake).show(supportFragmentManager,"Cake Dialog")
    }
}