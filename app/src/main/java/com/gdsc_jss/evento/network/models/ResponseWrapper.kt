package com.gdsc_jss.evento.network.models

data class Wrapper<T: Any>(
    val success: Boolean,
    val data: T
)