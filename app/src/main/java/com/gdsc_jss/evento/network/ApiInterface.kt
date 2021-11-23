package com.gdsc_jss.evento.network

import com.gdsc_jss.evento.network.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    companion object {
        const val LOGIN = "api/user/login"
        const val REGISTER = "api/user"
        const val GET_EVENTS = "api/user/events"
    }

    @GET(GET_EVENTS)
    suspend fun getEvents(): Response<Wrapper<ArrayList<EventResponse>>>

    @POST(LOGIN)
    suspend fun login(@Body signInBody: SignInBody): Response<Wrapper<User>>

    @POST(REGISTER)
    suspend fun signUp(@Body signUpBody: SignUpBody): Response<Wrapper<Any>>

}