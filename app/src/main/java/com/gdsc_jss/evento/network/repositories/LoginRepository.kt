package com.gdsc_jss.evento.network.repositories

import com.gdsc_jss.evento.network.ApiInterface
import com.gdsc_jss.evento.network.SafeApiRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(private val networkApi: ApiInterface) :
    SafeApiRequest() {

}