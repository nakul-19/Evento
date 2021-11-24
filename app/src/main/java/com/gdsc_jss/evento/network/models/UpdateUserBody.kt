package com.gdsc_jss.evento.network.models

data class UpdateUserBody(
    val branch: String,
    val name: String,
    val image: String?,
    val phone: String?,
    val email: String?,
    val section: String,
    val year: Int,
    val collegeId: String
)
