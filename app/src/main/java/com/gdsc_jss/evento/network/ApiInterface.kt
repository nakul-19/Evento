package com.gdsc_jss.evento.network

import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.network.models.SignInResponse
import com.gdsc_jss.evento.network.models.SignUpResponse
import com.gdsc_jss.evento.network.models.SignupBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    companion object {
        const val LOGIN = "api/user/login"
        const val REGISTER = "api/user"
    }

    @POST(LOGIN)
    suspend fun login(@Body signBody: SignInBody): Response<SignInResponse>

    @POST(REGISTER)
    suspend fun signUp(@Body signupBody: SignupBody): Response<SignUpResponse>

}