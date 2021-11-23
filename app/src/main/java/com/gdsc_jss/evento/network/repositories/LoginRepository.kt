package com.gdsc_jss.evento.network.repositories

import com.gdsc_jss.evento.network.ApiInterface
import com.gdsc_jss.evento.network.SafeApiRequest
import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.network.models.SignInResponse
import com.gdsc_jss.evento.network.models.SignUpResponse
import com.gdsc_jss.evento.network.models.SignupBody
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


class LoginRepository @Inject constructor(private val networkApi: ApiInterface) :
    SafeApiRequest() {

    suspend fun signIn(signBody: SignInBody): SignInResponse =
        apiRequest { networkApi.login(signBody) }

    suspend fun signUp(signupBody: SignupBody): SignUpResponse =
        apiRequest { networkApi.signUp(signupBody) }

}