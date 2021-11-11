package com.gdsc_jss.evento.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdsc_jss.evento.databinding.FragmentSignInBinding
import com.gdsc_jss.evento.viewmodels.SignInViewModel

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by activityViewModels()
    private lateinit var b: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return b.root
    }

}