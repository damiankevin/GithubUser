package com.android.githubuser.screens.user

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.githubuser.R
import com.android.githubuser.models.user.User
import com.android.githubuser.models.user.UserItem
import com.android.githubuser.platform.BaseFragment
import com.android.githubuser.platform.BaseViewModelFactory
import com.android.githubuser.platform.LiveDataWrapper
import kotlinx.android.synthetic.main.fragment_main_activity.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject


/**
 * User Fragment.
 * Handles UI.
 * Fires rest api initiation.
 */
class UserActivityFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {

    companion object{
        fun getMainActivityFragment() = UserActivityFragment()
    }

    //---------------Class variables---------------//

    val mUserUseCase : UserUseCase by inject()
    private val mBaseViewModelFactory : BaseViewModelFactory =
        BaseViewModelFactory(Dispatchers.Main, Dispatchers.IO, mUserUseCase)
    private val TAG = UserActivityFragment::class.java.simpleName
    var mDemoParam = "github"
    lateinit var mRecyclerViewAdapter: UserRecyclerViewAdapter

    private val mViewModel : UserActivityViewModel by lazy {
        ViewModelProviders.of(this, mBaseViewModelFactory)
            .get(UserActivityViewModel::class.java)    }
    var mPage = 1

    //---------------Life Cycle---------------//

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Start observing the targets
        this.mViewModel.mUserResponse.observe(this, this.mDataObserver)
        this.mViewModel.mLoadingLiveData.observe(this, this.loadingObserver)

        swipeRefreshLayout.setOnRefreshListener(this)
        mRecyclerViewAdapter = UserRecyclerViewAdapter(activity!!, arrayListOf())
        landingListRecyclerView.adapter = mRecyclerViewAdapter
        landingListRecyclerView.layoutManager = LinearLayoutManager(activity!!)

        initializeButtonScroll()

        this.mViewModel.requestUserActivityData(mDemoParam, mPage)
    }

    private fun initializeButtonScroll() {
        buttonSearch.setOnClickListener {
            if(editTextQuery.text.toString() == ""){
                showToast("Search box cannot be empty")
            }else{
                hideKeyboard()
                error_holder.visibility = View.GONE
                mRecyclerViewAdapter.mItemList.clear()
                mRecyclerViewAdapter.notifyDataSetChanged()
                mPage=1
                mDemoParam = editTextQuery.text.toString()
                this.mViewModel.requestUserActivityData(mDemoParam, mPage)
            }
        }

        landingListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPage++
                    mViewModel.requestUserActivityData(mDemoParam, mPage)
                }
            }
        })

    }

    //---------------Observers---------------//
    private val mDataObserver = Observer<LiveDataWrapper<User>> { result ->
        when (result?.responseStatus) {
            LiveDataWrapper.RESPONSESTATUS.LOADING -> {
                // Loading data
            }
            LiveDataWrapper.RESPONSESTATUS.ERROR -> {
                // Error for http request
                logD(TAG, "LiveDataResult.Status.ERROR = ${result.response}")
                error_holder.visibility = View.VISIBLE
                showToast("Error in getting data")

            }
            LiveDataWrapper.RESPONSESTATUS.SUCCESS -> {
                // Data from API
                logD(TAG, "LiveDataResult.Status.SUCCESS = ${result.response}")
                val mainItemReceived = result.response as User
                val listItems = mainItemReceived.items as ArrayList<UserItem>
                processData(listItems)
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_activity

    /**
     * Handle success data
     */
    private fun processData(listItems: ArrayList<UserItem>) {
        logD(TAG, "processData called with data ${listItems.size}")
        logD(TAG, "processData listItems =  ${listItems}")

        val refresh = Handler(Looper.getMainLooper())
        if(mRecyclerViewAdapter.mItemList.size==0){
            refresh.post {
                mRecyclerViewAdapter.updateListItems(listItems)
            }
        }else{
            refresh.post {
                mRecyclerViewAdapter.addListItems(listItems)
            }
        }

    }

    /**
     * Hide / show loader
     */
    private val loadingObserver = Observer<Boolean> { visible ->
        // Show hide progress bar
        if(visible){
            progress_circular.visibility = View.VISIBLE
        }else{
            progress_circular.visibility = View.INVISIBLE
        }
    }

    override fun onRefresh() {
        error_holder.visibility = View.GONE
        swipeRefreshLayout?.isRefreshing = false
        mRecyclerViewAdapter.mItemList.clear()
        mRecyclerViewAdapter.notifyDataSetChanged()
        this.mViewModel.requestUserActivityData(mDemoParam, mPage)
    }

    fun hideKeyboard() {
        activity?.currentFocus?.let { view ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}