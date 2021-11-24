package com.gdsc_jss.evento.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentSignInBinding
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.SignInBody
import com.gdsc_jss.evento.ui.students.StudentActivity
import com.gdsc_jss.evento.util.handleErrorsWithSnackbar
import com.gdsc_jss.evento.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by activityViewModels()
    private lateinit var b: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSignInBinding.inflate(layoutInflater, container, false)

        setCLicks()
        observers()

        return b.root
    }

    private fun setCLicks() {
        b.loginBtn.setOnClickListener {
            if (isValid()) {
                val signInBody = SignInBody(
                    collegeId = b.emailText.text.toString().toUpperCase(Locale.getDefault()),
                    password = b.passwordText.text.toString()
                )
                Timber.d(signInBody.toString())
                viewModel.signIn(signInBody)
            }
        }

        b.signUpBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.login_frame, SignUpFragment())?.commit()
        }
    }

    private fun observers() {
        viewModel.signInUser.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    b.loginBtn.text = "Login"
                    b.loginBtn.isEnabled = true
                    startActivity(Intent(requireActivity(), StudentActivity::class.java))
                    activity?.finish()
                }
                is Resource.Loading -> {
                    b.loginBtn.text = "Loading.."
                    b.loginBtn.isEnabled = false
                }
                is Resource.Error -> {
                    b.loginBtn.text = "Login"
                    b.loginBtn.isEnabled = true
                    handleErrorsWithSnackbar(
                        b.root,
                        response.msg
                    )
                }
            }
        })
    }

    private fun isValid(): Boolean {
        if (b.emailText.text.toString().isBlank()) {
            b.emailText.error = "Required field"
            return false
        } else if (b.passwordText.text.toString().isBlank()) {
            b.passwordText.error = "Required field"
            return false
        }
        return true
    }

}