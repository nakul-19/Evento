package com.gdsc_jss.evento.network

import com.gdsc_jss.evento.network.models.UserResponse

sealed class AuthResource() {
    class Authenticated(val user: UserResponse) : AuthResource()
    object Authenticating : AuthResource()
    object UnAuthenticated : AuthResource()
}
