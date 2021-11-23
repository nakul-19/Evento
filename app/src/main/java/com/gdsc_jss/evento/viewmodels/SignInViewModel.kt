package com.gdsc_jss.evento.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_jss.evento.network.ApiException
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.network.models.User
import com.gdsc_jss.evento.network.models.Wrapper
import com.gdsc_jss.evento.network.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

    private val _signInUser: MutableLiveData<Resource<Wrapper<User>>> = MutableLiveData()
    val signInUser: LiveData<Resource<Wrapper<User>>> = _signInUser

    fun signIn(signBody: SignInBody) = viewModelScope.launch {
        _signInUser.postValue(Resource.Loading())
        try {
            _signInUser.postValue(Resource.Success(repo.signIn(signBody)))
        } catch (e: Exception) {
            Timber.d(e.message.toString())
            if (e is ApiException) {
                _signInUser.postValue(Resource.Error(e.msg))
            } else {
                _signInUser.postValue(Resource.Error("Something went wrong!"))
            }
        }
    }

}