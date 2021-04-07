package com.android.githubuser.models.user

import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Sub data class for sample response.
 */
data class UserItem (
    @JsonProperty("login")
    val login : String,

    @JsonProperty("id")
    val id : Int,

    @JsonProperty("node_id")
    val node_id : String,

    @JsonProperty("avatar_url")
    val avatar_url : String,

    @JsonProperty("gravatar_id")
    val gravatar_id : String,

    @JsonProperty("url")
    val url : String,

    @JsonProperty("html_url")
    val html_url : String,

    @JsonProperty("followers_url")
    val followers_url : String,

    @JsonProperty("type")
    val type : String,

    @JsonProperty("site_admin")
    val site_admin : Boolean,

    @JsonProperty("score")
    val score : Int,

)