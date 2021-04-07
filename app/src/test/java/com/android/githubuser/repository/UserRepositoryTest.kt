package com.android.githubuser.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.githubuser.base.BaseUTTest
import com.android.githubuser.di.configureTestAppComponent
import com.android.githubuser.network.user.UserAPIService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class UserRepositoryTest : BaseUTTest(){

    //Target
    private lateinit var mRepo: UserRepository
    //Inject api service created with koin
    val mAPIService : UserAPIService by inject()
    //Inject Mockwebserver created with koin
    val mockWebServer : MockWebServer by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val mQuery = "github"
    val mPage = 1
    val mCount = 97145

    @Before
    fun start(){
        super.setUp()

        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_user_repo_retrieves_expected_data() =  runBlocking<Unit>{

        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        mRepo = UserRepository()

        val dataReceived = mRepo.getUserData(mQuery,mPage)

        assertNotNull(dataReceived)
        assertEquals(dataReceived.total_count, mCount)
    }
}