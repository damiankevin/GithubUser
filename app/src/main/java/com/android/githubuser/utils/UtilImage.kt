package com.android.githubuser.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide

object UtilImage {

    fun loadImage(view: ImageView?, imagePath: String?, context: Context) {
        val safeImagePath = if (!imagePath.isNullOrBlank()) imagePath else ""
        view?.let {
            Glide.with(context).load(safeImagePath)
                .fallback(android.R.drawable.stat_notify_error)
                .timeout(4500)
                .into(view)
        }
    }


}