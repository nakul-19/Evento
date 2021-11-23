package com.gdsc_jss.evento.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentSignUpBinding
import com.gdsc_jss.evento.network.models.SignUpBody
import com.gdsc_jss.evento.viewmodels.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.*

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by activityViewModels()
    private lateinit var b: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        initViews()
        setClicks()

        return b.root
    }

    private fun initViews() {
        val genderList = arrayOf("Select", "Male", "Female")
        val yearList = arrayOf(1, 2, 3, 4)
        val genderAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                genderList
            )
        b.genderSpinner.adapter = genderAdapter

        val yearAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, yearList)
        b.yearSpinner.adapter = yearAdapter
    }

    private fun setClicks() {
        b.nextBtn.setOnClickListener {
            if (isValid()) {
                val signupBody = SignUpBody(
                    branch = branchText.text.toString(),
                    collegeId = admissionText.text.toString().uppercase(Locale.getDefault()),
                    gender = genderSpinner.selectedItem.toString(),
                    image = "",
                    name = nameText.text.toString(),
                    password = passwordText.text.toString(),
                    section = sectionText.text.toString(),
                    year = yearSpinner.selectedItem.toString().toInt(),
                    email = emailText.text.toString(),
                    phone = mobileText.text.toString()
                )
                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.login_frame, EditAvatarFragment(signupBody))?.commit()
            }
        }

        b.loginBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.login_frame, SignInFragment())?.commit()
        }
    }

    private fun isValid(): Boolean {
        when {
            b.nameText.text.toString().isBlank() -> {
                b.nameText.error = "Required field"
                return false
            }
            b.emailText.text.toString().isBlank() -> {
                b.emailText.error = "Required field"
                return false
            }
            b.mobileText.text.toString().isBlank() -> {
                b.mobileText.error = "Required field"
                return false
            }
            b.genderSpinner.selectedItemPosition == 0 -> {
                ((b.genderSpinner.selectedView) as TextView).error = "Select gender"
                return false
            }
            b.branchText.text.toString().isBlank() -> {
                b.branchText.error = "Required field"
                return false
            }
            b.sectionText.text.toString().isBlank() -> {
                b.sectionText.error = "Required field"
                return false
            }
            b.admissionText.text.toString().isBlank() -> {
                b.admissionText.error = "Required field"
                return false
            }
            else -> return true
        }
    }

}