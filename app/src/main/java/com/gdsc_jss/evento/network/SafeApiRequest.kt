package com.gdsc_jss.evento.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber.*

abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call : suspend() -> Response<T>): T {

        val response = call.invoke()

        if (response.isSuccessful && response.body() != null) {
            i(response.body().toString())
            return response.body()!!
        }
        else {
            e(response.code().toString())
            e(response.message())

            var msg = "Something went wrong!"
            val error = response.errorBody()?.string()

            error?.let {
                try {
                    msg += JSONObject(it).getString("detail")
                } catch (e: JSONException) {
                    msg = response.message()
                }
            }

            throw ApiException(msg,response.code())
        }

    }

}

class ApiException(val msg: String, val code: Int) : Exception(msg)
