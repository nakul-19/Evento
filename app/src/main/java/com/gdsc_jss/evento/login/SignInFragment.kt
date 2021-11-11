package com.gdsc_jss.evento.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdsc_jss.evento.databinding.FragmentSignInBinding

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}