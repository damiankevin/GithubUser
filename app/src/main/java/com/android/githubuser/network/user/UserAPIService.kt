package com.android.githubuser.network.user

import com.android.githubuser.models.user.User
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * User service Retrofit API.
 */
interface UserAPIService{

    @GET("search/users")
    suspend fun getUser(
        @Query("q") q:String,
        @Query("page") page:Int,

    ): User

}