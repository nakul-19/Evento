package com.gdsc_jss.evento.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gdsc_jss.evento.network.ApiException
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.UpdateUserBody
import com.gdsc_jss.evento.network.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {

    companion object {
        private val mUser = MutableLiveData<AuthResource>(AuthResource.Authenticating)
        val user: LiveData<AuthResource> = mUser

        fun authenticating() {
            mUser.postValue(AuthResource.Authenticating)
        }
    }

    @DelicateCoroutinesApi
    fun getUser() {
        GlobalScope.launch {
            mUser.postValue(AuthResource.Authenticating)
            try {
                val result = repo.getUser()
                Timber.d(result.toString())
                mUser.postValue(AuthResource.Authenticated(result.data))
            } catch (e: Exception) {
                if (e is ApiException) {
                    mUser.postValue(AuthResource.UnAuthenticated)
                } else {
                    delay(5000L)
                    this@UserViewModel.getUser()
                }
            }
        }
    }

    fun logoutUser() {
        repo.logout()
        if (mUser.value != AuthResource.UnAuthenticated)
            mUser.value = AuthResource.UnAuthenticated
    }

    @DelicateCoroutinesApi
    fun isLoggedIn(): Boolean {
        return if (repo.isLoggedIn()) {
            Timber.d("Stored")
            this.getUser()
            true
        } else false
    }

    fun updateUser(updateUserBody: UpdateUserBody) = liveData<Resource<Any>> {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.updateUser(updateUserBody)))
        } catch (e: Exception) {
            Timber.d(e.message.toString())
            if (e is ApiException) {
                emit((Resource.Error(e.msg)))
            } else {
                emit(Resource.Error("Something went wrong!"))
            }
        }
    }

}