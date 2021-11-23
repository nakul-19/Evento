package com.gdsc_jss.evento.network.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("data")
    val data: Data,
    val success: Boolean
)

data class Data(
    @SerializedName("_id")
    val id: String,
    val branch: String,
    val collegeId: String,
    val gender: String,
    val name: String,
    val section: String,
    val year: Int
)