package com.android.githubuser.screens.user

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.githubuser.R
import com.android.githubuser.models.user.UserItem
import com.android.githubuser.utils.UtilImage
import de.hdodenhof.circleimageview.CircleImageView


class UserRecyclerViewAdapter(val context: Context, list: ArrayList<UserItem>): RecyclerView.Adapter<UserRecyclerViewAdapter.UserFragViewHolder>() {

    var mItemList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFragViewHolder {
        return UserFragViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.landing_list_view_item,
                parent,
                false
            )
        )
    }

    fun updateListItems(updatedList: ArrayList<UserItem>){
        mItemList.clear()
        mItemList = updatedList
        notifyDataSetChanged()
    }

    fun addListItems(addedList: ArrayList<UserItem>){
        mItemList.addAll(addedList)
        notifyItemRangeInserted(itemCount, addedList.size)
    }
    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun onBindViewHolder(holder: UserFragViewHolder, position: Int) {
        val model : UserItem = mItemList[position]
        holder.personName.text = model.login
        holder.urlAccount.text = model.html_url
        model.avatar_url.let {
            UtilImage.loadImage(holder.avatarUser, it, context)
        }
    }

    class UserFragViewHolder(item: View): RecyclerView.ViewHolder(item){
        val personName : TextView = item.findViewById(R.id.personName)
        val urlAccount : TextView = item.findViewById(R.id.urlAccount)
        val avatarUser : CircleImageView = item.findViewById(R.id.userAvatar)
    }
}