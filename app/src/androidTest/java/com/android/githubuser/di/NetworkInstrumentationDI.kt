package com.android.githubuser.di

import com.android.githubuser.network.user.UserAPIService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit Koin DI component for Instrumentation Testing
 */
fun configureNetworkForInstrumentationTest(baseTestApi: String) = module {

    single {
        Retrofit.Builder()
            .baseUrl(baseTestApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    factory{ get<Retrofit>().create(UserAPIService::class.java) }
}

