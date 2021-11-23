package com.gdsc_jss.evento.network

sealed class Resource<T : Any> {
    class Success<T : Any>(val r: T, val msg: String? = null) : Resource<T>()
    class Error<T : Any>(val msg: String) : Resource<T>()
    class Loading<T : Any>() : Resource<T>()
    class Refreshing<T : Any>() : Resource<T>()
}