package com.android.githubuser.models.user

import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Parent Data class for sample response.
 */
data class User(

    @JsonProperty("total_count")
    val total_count : Int,

    @JsonProperty("incomplete_results")
    val incomplete_results : Boolean,

    @JsonProperty("items")
    val items : ArrayList<UserItem>

)