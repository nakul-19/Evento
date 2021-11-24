package com.gdsc_jss.evento.network.repositories

import android.content.SharedPreferences
import com.gdsc_jss.evento.network.ApiInterface
import com.gdsc_jss.evento.network.SafeApiRequest
import com.gdsc_jss.evento.network.models.UpdateUserBody
import com.gdsc_jss.evento.util.token
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkApi: ApiInterface,
    private val sp: SharedPreferences
) : SafeApiRequest() {

    suspend fun getUser() = apiRequest { networkApi.getUser(sp.getString(token, "").orEmpty()) }

    fun isLoggedIn(): Boolean {
        return sp.getString(token, "")?.isNotBlank() ?: false
    }

    fun logout() {
        sp.edit().clear().apply()
    }

    suspend fun updateUser(updateUserBody: UpdateUserBody) = apiRequest {
        networkApi.updateUser(
            sp.getString(
                token, ""
            ).orEmpty(), updateUserBody
        )
    }
}