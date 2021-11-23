package com.gdsc_jss.evento.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_jss.evento.network.ApiException
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.network.models.SignInResponse
import com.gdsc_jss.evento.network.models.Wrapper
import com.gdsc_jss.evento.network.repositories.LoginRepository
import com.gdsc_jss.evento.util.token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: LoginRepository,
    private val sp: SharedPreferences
) : ViewModel() {

    private val _signInUser: MutableLiveData<Resource<Wrapper<SignInResponse>>> = MutableLiveData()
    val signInUser: LiveData<Resource<Wrapper<SignInResponse>>> = _signInUser

    fun signIn(signBody: SignInBody) = viewModelScope.launch {
        _signInUser.postValue(Resource.Loading())
        try {
            val result = repo.signIn(signBody)
            Timber.d(result.toString())
            storeResult(result.data)
            UserViewModel.authenticating()
            _signInUser.postValue(Resource.Success(result))
        } catch (e: Exception) {
            Timber.d(e.message.toString())
            if (e is ApiException) {
                _signInUser.postValue(Resource.Error(e.msg))
            } else {
                _signInUser.postValue(Resource.Error("Something went wrong!"))
            }
        }
    }

    private fun storeResult(result: SignInResponse) {
        Timber.d(result.token)
        sp.edit().putString(token, "Bearer " + result.token).apply()
    }

}