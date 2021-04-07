package com.android.githubuser.di

import com.android.githubuser.utils.AppUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * DI module for App Util dependency.
 */
val AppUtilDependency = module {

    factory { AppUtils(androidContext()) }
}