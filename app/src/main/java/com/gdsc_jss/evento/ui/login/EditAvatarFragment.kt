package com.gdsc_jss.evento.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdsc_jss.evento.databinding.FragmentEditAvatarBinding
import com.gdsc_jss.evento.network.models.SignupBody
import timber.log.Timber


class EditAvatarFragment(val signupBody: SignupBody) : Fragment() {

    private lateinit var binding: FragmentEditAvatarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAvatarBinding.inflate(layoutInflater, container, false)

        initViews()

        Timber.d(signupBody.toString())

        return binding.root
    }

    private fun initViews() {
        binding.displayName.text = signupBody.name.toString()
        binding.displayEmail.text = signupBody.email.toString()
    }
}