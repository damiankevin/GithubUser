package com.android.githubuser.repository

import com.android.githubuser.models.user.User
import com.android.githubuser.network.user.UserAPIService
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserRepository : KoinComponent {

    val mUserAPIService: UserAPIService by inject()

    /**
     * Request data from backend
     */
    suspend fun getUserData(query: String, mPage: Int): User {

        return processDataFetchLogic(query,mPage)

    }

    suspend fun processDataFetchLogic(parameter: String, mPage: Int): User {

        return mUserAPIService.getUser(parameter, mPage)
    }

}


