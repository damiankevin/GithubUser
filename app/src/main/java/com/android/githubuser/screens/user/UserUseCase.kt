package com.android.githubuser.screens.user

import com.android.githubuser.models.user.User
import com.android.githubuser.repository.UserRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * Use Case class for handling Login flow.
 * Requests data from Repo.
 * Process received data into required model and reverts back to ViewModel.
 */
class UserUseCase : KoinComponent {

    val mUserRepo : UserRepository by inject()

    suspend fun processUserUseCase(query: String, mPage: Int) : User {

        val response =  mUserRepo.getUserData(query,mPage)


        return response
    }
}