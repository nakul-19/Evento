package com.gdsc_jss.evento.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_jss.evento.network.ApiException
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.EventResponse
import com.gdsc_jss.evento.network.repositories.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UpcomingEventsViewModel @Inject constructor(private val repo: EventRepository) : ViewModel() {

    private val mEvents = MutableLiveData<Resource<ArrayList<EventResponse>>>()
    val events: LiveData<Resource<ArrayList<EventResponse>>> = mEvents

    fun getEvents() = viewModelScope.launch {
        mEvents.value = Resource.Loading()
        try {
            mEvents.postValue(Resource.Success(repo.getAllEvents().data))
        } catch (e: Exception) {
            Timber.e(e.message.toString())
            if (e is ApiException) {
                mEvents.postValue(Resource.Error(e.msg))
            } else {
                mEvents.postValue(Resource.Error("Something went wrong!"))
            }
        }
    }

    fun refreshEvents() = viewModelScope.launch {
        mEvents.value = Resource.Refreshing()
        try {
            mEvents.postValue(Resource.Success(repo.getAllEvents().data))
        } catch (e: Exception) {
            Timber.e(e.message.toString())
            if (e is ApiException) {
                mEvents.postValue(Resource.Error(e.msg))
            } else {
                mEvents.postValue(Resource.Error("Something went wrong!"))
            }
        }
    }

}