package com.gdsc_jss.evento.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gdsc_jss.evento.network.ApiException
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.repositories.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepo: EventRepository): ViewModel() {

    fun registerForEvent(id:String) = liveData<Resource<Any>>(Dispatchers.Default) {
        emit(Resource.Loading())
        try {
            val result = eventRepo.registerForEvent(id)
            emit(Resource.Success(result.data))
        } catch (e : Exception) {
            Timber.e(e.message.toString())
            if (e is ApiException)
                emit(Resource.Error(e.msg))
            else
                emit(Resource.Error("Something went wrong!"))
        }
    }

}