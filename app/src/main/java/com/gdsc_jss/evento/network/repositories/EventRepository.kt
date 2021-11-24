package com.gdsc_jss.evento.network.repositories

import android.content.SharedPreferences
import com.gdsc_jss.evento.network.ApiInterface
import com.gdsc_jss.evento.network.SafeApiRequest
import com.gdsc_jss.evento.util.token
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val networkApi: ApiInterface,
    private val sp: SharedPreferences
) :
    SafeApiRequest() {

    suspend fun getAllEvents() = apiRequest { networkApi.getEvents() }

    suspend fun getEventsById(list: List<String>) =
        apiRequest { networkApi.getMultipleEventsById(list) }

    suspend fun registerForEvent(id: String) = apiRequest {
        networkApi.registration(sp.getString(token, "").orEmpty(), id)
    }
}