package com.gdsc_jss.evento.network.models

data class UserResponse(
    val __v: Int,
    val _id: String,
    val branch: String,
    val collegeId: String,
    val createdAt: String,
    val gender: String,
    val name: String,
    val registeredIn: List<String>,
    val section: String,
    val tokens: List<String>,
    val updatedAt: String,
    val year: Int
)