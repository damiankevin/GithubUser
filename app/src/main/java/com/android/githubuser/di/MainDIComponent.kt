package com.android.githubuser.di

/**
 * Main dependency component.
 * This will create and provide required dependencies with sub dependencies.
 */
val appComponent = listOf(UseCaseDependency, AppUtilDependency, NetworkDependency, SharedPrefDependency, RepoDependency)