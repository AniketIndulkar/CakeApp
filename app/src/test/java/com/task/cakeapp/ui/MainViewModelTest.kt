package com.task.cakeapp.ui

import androidx.activity.viewModels
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.task.cakeapp.CakeApplication
import com.task.cakeapp.util.Injector
import com.task.cakeapp.util.Util
import junit.framework.TestCase
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Null
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainViewModelTest : TestCase() {

    private val context = ApplicationProvider.getApplicationContext() as CakeApplication // getting Context object from Robolectric

    private val mainViewModel: MainViewModel =Injector.provideMainViewModelFactory(context)
        .create(MainViewModel::class.java)//Getting ViewModel instance from Custom viewmodel factory class

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onStart() = runBlockingTest {
            mainViewModel.getAllCakesFromAPI()// To get Data from API before test starts
    }

    @Test
    fun testGetAllCakes() {
        //More cases cab be added based on internet connectivity and Data availability
        val value = mainViewModel.allCakes.value // All the data we get from API is sorted by default and comes after removing duplicates
        assertThat(value, not(nullValue()))// Data should not be null
        assertThat(value!!.size,not(0))// And size of data list should not be zero
    }

    //Live data to emmit what is the data source
    // 1 - No data available
    //2 - Data from API
    //3 - Data from Local Database without internet connectivity
    @Test
    fun testIsLoading() {
        //This test is to check weather data loaded or not
        //More cases cab be added based on internet connectivity and Data availability
        val value = mainViewModel.dataSource.value
        if (Util.isOnline(context)){// if internet is connected
            assertThat(value, not(nullValue()))// data source value should not be null
            assertThat(value,not(1))// data source value should not be one
            assertEquals(value,2)// If internet is not connected
        }else{
            assertEquals(value,3)// If internet is not connected data should be loaded from Local database considering data loaded first time using API
        }
    }
}