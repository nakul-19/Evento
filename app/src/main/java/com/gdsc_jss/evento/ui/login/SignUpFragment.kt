package com.gdsc_jss.evento.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentSignUpBinding
import com.gdsc_jss.evento.viewmodels.SignUpViewModel

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by activityViewModels()
    private lateinit var b: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        setClicks()

        return b.root
    }

    private fun setClicks() {
        b.nextBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.login_frame, EditAvatarFragment())?.commit()
        }
    }

}