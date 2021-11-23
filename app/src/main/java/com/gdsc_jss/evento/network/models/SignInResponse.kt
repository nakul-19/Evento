package com.gdsc_jss.evento.network.models

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("data")
    val user: User,
    val success: Boolean
)

data class User(
    @SerializedName("_id")
    val id: String,
    val branch: String,
    val collegeId: String,
    val gender: String,
    val name: String,
    val section: String,
    val token: String,
    val year: Int
)