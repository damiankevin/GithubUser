package com.android.githubuser.screens.user

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.android.githubuser.R
import com.android.githubuser.base.BaseUITest
import com.android.githubuser.di.generateTestAppComponent
import com.android.githubuser.helpers.recyclerItemAtPosition
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class UserActivityTest : BaseUITest(){

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(UserActivity::class.java, true, false)

    val mLoginUseCase : UserUseCase by inject()
    val  mMockWebServer : MockWebServer by inject()

    val mNameTestOne = "github"
    val mURLTestOne = "https://github.com/github"
    val mNameTestTwo = "githubstudent"
    val mURLTestTwo = "https://github.com/githubstudent"

    @Before
    fun start(){
        super.setUp()
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun test_recyclerview_elements_for_expected_response() {
        mActivityTestRule.launchActivity(null)

        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        //Check if item at 0th position is having 0th element in json
        Espresso.onView(ViewMatchers.withId(R.id.landingListRecyclerView))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(mNameTestOne))
                    )
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.landingListRecyclerView))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(mURLTestOne))
                    )
                )
            )

        //Scroll to last index in json
        Espresso.onView(ViewMatchers.withId(R.id.landingListRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<UserRecyclerViewAdapter.UserFragViewHolder>(9))

        //Check if item at 9th position is having 9th element in json
        Espresso.onView(ViewMatchers.withId(R.id.landingListRecyclerView))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        9,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(mNameTestTwo))
                    )
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.landingListRecyclerView))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        9,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(mURLTestTwo))
                    )
                )
            )

    }
}