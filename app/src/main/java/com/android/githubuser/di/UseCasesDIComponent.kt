package com.android.githubuser.di

import com.android.githubuser.screens.user.UserUseCase
import org.koin.dsl.module

/**
 * Use case DI module.
 * Provide Use case dependency.
 */
val UseCaseDependency = module {

    factory {
        UserUseCase()
    }
}