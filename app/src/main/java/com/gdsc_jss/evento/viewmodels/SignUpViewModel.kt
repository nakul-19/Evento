package com.gdsc_jss.evento.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_jss.evento.network.ApiException
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.network.models.SignUpBody
import com.gdsc_jss.evento.network.models.Wrapper
import com.gdsc_jss.evento.network.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

    private val _signUpUser: MutableLiveData<Resource<Wrapper<Any>>> = MutableLiveData()
    val signUpUser: LiveData<Resource<Wrapper<Any>>> = _signUpUser

    fun signUp(signUpBody: SignUpBody) = viewModelScope.launch {
        _signUpUser.postValue(Resource.Loading())
        try {
            _signUpUser.postValue(Resource.Success(repo.signUp(signUpBody)))
        } catch (e: Exception) {
            Timber.d(e.message.toString())
            if (e is ApiException) {
                _signUpUser.postValue(Resource.Error(e.msg))
            } else {
                _signUpUser.postValue(Resource.Error("Something went wrong!"))
            }
        }
    }
}