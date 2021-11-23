package com.gdsc_jss.evento.network.repositories

import com.gdsc_jss.evento.network.ApiInterface
import com.gdsc_jss.evento.network.SafeApiRequest
import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.network.models.SignUpBody
import com.gdsc_jss.evento.network.models.SignInResponse
import com.gdsc_jss.evento.network.models.Wrapper
import javax.inject.Inject


class LoginRepository @Inject constructor(private val networkApi: ApiInterface) :
    SafeApiRequest() {

    suspend fun signIn(signInBody: SignInBody): Wrapper<SignInResponse> =
        apiRequest { networkApi.login(signInBody) }

    suspend fun signUp(signUpBody: SignUpBody): Wrapper<Any> =
        apiRequest { networkApi.signUp(signUpBody) }

}