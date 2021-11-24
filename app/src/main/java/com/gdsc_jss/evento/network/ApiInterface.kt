package com.gdsc_jss.evento.network

import com.gdsc_jss.evento.network.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    companion object {
        const val LOGIN = "api/user/login"
        const val REGISTER = "api/user"
        const val GET_USER = "api/user"
        const val GET_EVENTS = "api/user/events"
        const val UPDATE_USER = "api/user"
        const val REGISTER_FOR_EVENT = "api/registration/{id}"
    }

    @GET(GET_USER)
    suspend fun getUser(@Header("Authorization") token: String): Response<Wrapper<UserResponse>>

    @GET(GET_EVENTS)
    suspend fun getEvents(): Response<Wrapper<ArrayList<EventResponse>>>

    @GET(GET_EVENTS)
    suspend fun getMultipleEventsById(@Query("_id") list: List<String>): Response<Wrapper<ArrayList<EventResponse>>>

    @POST(REGISTER_FOR_EVENT)
    suspend fun registration(
        @Header("Authorization") token: String,
        @Path("id") eventId: String
    ): Response<Wrapper<Any>>

    @POST(LOGIN)
    suspend fun login(@Body signInBody: SignInBody): Response<Wrapper<SignInResponse>>

    @POST(REGISTER)
    suspend fun signUp(@Body signUpBody: SignUpBody): Response<Wrapper<Any>>

    @PUT(UPDATE_USER)
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body updateUserBody: UpdateUserBody
    ): Response<Wrapper<Any>>

}