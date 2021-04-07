package com.android.githubuser.screens.user

import android.os.Bundle
import com.android.githubuser.R
import com.android.githubuser.platform.BaseActivity

/**
 * Activity holder for Login Flow.
 */
class UserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureAndShowFragment()
    }

    private fun configureAndShowFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.base_frame_layout) as UserActivityFragment?
        if(fragment == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.base_frame_layout, UserActivityFragment.getMainActivityFragment())
                .commit()
        }
    }
}
