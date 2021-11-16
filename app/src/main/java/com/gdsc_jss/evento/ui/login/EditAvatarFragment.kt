package com.gdsc_jss.evento.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentEditAvatarBinding
import com.gdsc_jss.evento.databinding.FragmentLoginSignUpBinding


class EditAvatarFragment : Fragment() {

    private lateinit var binding: FragmentEditAvatarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAvatarBinding.inflate(layoutInflater, container, false)


        return binding.root
    }
}