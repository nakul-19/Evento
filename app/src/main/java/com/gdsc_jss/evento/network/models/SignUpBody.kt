package com.gdsc_jss.evento.network.models

data class SignUpBody(
    val branch: String,
    val collegeId: String,
    val gender: String,
    val image: String,
    val name: String,
    val password: String,
    val section: String,
    val year: Int,
    val email : String,
    val phone : String
)