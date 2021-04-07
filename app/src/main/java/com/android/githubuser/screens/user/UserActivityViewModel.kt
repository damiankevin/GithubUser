package com.android.githubuser.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.githubuser.models.user.User
import com.android.githubuser.platform.LiveDataWrapper
import kotlinx.coroutines.*
import org.koin.core.KoinComponent

class UserActivityViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val useCase: UserUseCase
) : ViewModel(), KoinComponent
{

    var mUserResponse = MutableLiveData<LiveDataWrapper<User>>()
    val mLoadingLiveData = MutableLiveData<Boolean>()
    private val job = SupervisorJob()
    val mUiScope = CoroutineScope(mainDispatcher + job)
    val mIoScope = CoroutineScope(ioDispatcher + job)

    init {
        resetValues()
    }

    fun resetValues() {

    }

    fun requestUserActivityData(param: String, mPage: Int) {
        mUiScope.launch {
            mUserResponse.value = LiveDataWrapper.loading()
            setLoadingVisibility(true)
            try {
                val data = mIoScope.async {
                    return@async useCase.processUserUseCase(param,mPage)
                }.await()
                try {
                    mUserResponse.value = LiveDataWrapper.success(data)
                } catch (e: Exception) {
                }
                setLoadingVisibility(false)
            } catch (e: Exception) {
                setLoadingVisibility(false)
                mUserResponse.value = LiveDataWrapper.error(e)
            }
        }
    }

    private fun setLoadingVisibility(visible: Boolean) {
        mLoadingLiveData.postValue(visible)
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}