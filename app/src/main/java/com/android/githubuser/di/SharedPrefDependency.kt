package com.android.githubuser.di

import com.android.githubuser.platform.SharedPreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Shared Preference DI Module.
 *
 */
val SharedPrefDependency = module {

    factory { SharedPreferenceHelper(androidContext()) }
}