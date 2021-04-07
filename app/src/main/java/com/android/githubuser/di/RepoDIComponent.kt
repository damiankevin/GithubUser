package com.android.githubuser.di

import com.android.githubuser.repository.UserRepository
import org.koin.dsl.module

/**
 * Repository DI module.
 * Provides Repo dependency.
 */
val RepoDependency = module {

    factory {
        UserRepository()
    }

}