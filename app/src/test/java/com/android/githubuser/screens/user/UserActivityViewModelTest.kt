package com.android.githubuser.screens.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.githubuser.base.BaseUTTest
import com.android.githubuser.di.configureTestAppComponent
import com.android.githubuser.models.user.User
import com.android.githubuser.platform.LiveDataWrapper
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin

@RunWith(JUnit4::class)
class UserActivityViewModelTest: BaseUTTest(){

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mUserActivityViewModel: UserActivityViewModel

    val mDispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var mUserUseCase: UserUseCase

    val mQuery = "github"
    val mPage = 1
    val mCount = 97145
    @Before
    fun start(){
        super.setUp()
        //Used for initiation of Mockk
        MockKAnnotations.init(this)
        //Start Koin with required dependencies
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_user_view_model_data_populates_expected_value(){

        mUserActivityViewModel = UserActivityViewModel(mDispatcher,mDispatcher,mUserUseCase)
        val sampleResponse = getJson("success_resp_list.json")
        var jsonObj = Gson().fromJson(sampleResponse, User::class.java)
        //Make sure login use case returns expected response when called
        coEvery { mUserUseCase.processUserUseCase(any(),any()) } returns jsonObj
        mUserActivityViewModel.mUserResponse.observeForever {  }

        mUserActivityViewModel.requestUserActivityData(mQuery,mPage)

        assert(mUserActivityViewModel.mUserResponse.value != null)
        assert(
            mUserActivityViewModel.mUserResponse.value!!.responseStatus
                    == LiveDataWrapper.RESPONSESTATUS.SUCCESS)
        val testResult = mUserActivityViewModel.mUserResponse.value as LiveDataWrapper<User>
        Assert.assertEquals(testResult.response!!.total_count, mCount)
    }
}