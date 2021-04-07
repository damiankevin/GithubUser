package com.android.githubuser.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.githubuser.screens.user.UserActivityViewModel
import com.android.githubuser.screens.user.UserUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Base VM Factory for creation of different VM's
 */
class BaseViewModelFactory constructor(
    private val mainDispather: CoroutineDispatcher
    ,private val ioDispatcher: CoroutineDispatcher
    ,private val useCase: UserUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserActivityViewModel::class.java)) {
            UserActivityViewModel(mainDispather, ioDispatcher,useCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not configured") as Throwable
        }
    }
}