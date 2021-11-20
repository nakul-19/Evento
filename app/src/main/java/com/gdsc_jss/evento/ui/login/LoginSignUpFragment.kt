package com.gdsc_jss.evento.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentLoginSignUpBinding
import com.gdsc_jss.evento.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginSignUpFragment : Fragment() {

    private lateinit var binding: FragmentLoginSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginSignUpBinding.inflate(layoutInflater, container, false)

        setClicks()

        return binding.root
    }

    private fun setClicks() {

        binding.loginBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.login_frame, SignInFragment())?.commit()
        }

        binding.signUpBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.login_frame, SignUpFragment())?.commit()
        }

    }
}