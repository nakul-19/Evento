package com.gdsc_jss.evento.ui.students

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentProfileBinding
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.network.models.UserResponse
import com.gdsc_jss.evento.ui.login.LoginActivity
import com.gdsc_jss.evento.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        setClicks()
        observers()

        return binding.root
    }

    private fun observers() {
        UserViewModel.user.observe(viewLifecycleOwner) { response ->
            when (response) {
                is AuthResource.Authenticated -> {
                    binding.progressBar.visibility = View.GONE
                    binding.editBtn.isEnabled = true
                    Timber.d(response.user.toString())
                    initViews(response.user)
                }
                is AuthResource.Authenticating -> {
                    binding.editBtn.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initViews(user: UserResponse) {
        if (user.image?.isNotBlank() == true) {
            Glide.with(binding.root).load(user.image).into(binding.avatar)
        } else {
            if (user.gender == "Female") {
                binding.avatar.setImageResource(R.drawable.avatar)
            }
        }

        binding.nameInput.text = user.name
        binding.emailInput.text = user.email ?: ""
        binding.mobileInput.text = user.phone ?: ""
        binding.branchInput.text = user.branch
        binding.sectionInput.text = user.section
        binding.yearInput.text = user.year.toString()
        binding.collegeIdInput.text = user.collegeId.toString()

    }

    private fun setClicks() {
        binding.editBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }

        binding.logout.setOnClickListener {
            val alertDialog =
                AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Logout")
            alertDialog.setMessage("Are you sure you want to logout ?")
            alertDialog.setPositiveButton(
                "Logout"
            ) { dialog, id ->
                dialog.dismiss()
                viewModel.logoutUser()
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog, id ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }
}