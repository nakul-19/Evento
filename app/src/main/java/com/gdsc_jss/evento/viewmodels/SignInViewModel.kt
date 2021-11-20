package com.gdsc_jss.evento.viewmodels

import androidx.lifecycle.ViewModel
import com.gdsc_jss.evento.network.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

}