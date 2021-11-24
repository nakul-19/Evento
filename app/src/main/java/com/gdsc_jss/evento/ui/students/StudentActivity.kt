package com.gdsc_jss.evento.ui.students

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.ActivityStudentBinding
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.ui.common.UpcomingEventsFragment
import com.gdsc_jss.evento.ui.login.LoginActivity
import com.gdsc_jss.evento.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
class StudentActivity : AppCompatActivity() {

    private lateinit var b: ActivityStudentBinding
    private val viewModel: UserViewModel by viewModels()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(b.root)

        observeAuth()

        setBottomBar()
    }

    @DelicateCoroutinesApi
    private fun observeAuth() {
        UserViewModel.user.observe(this) {
            when (it) {
                is AuthResource.UnAuthenticated -> {
                    viewModel.logoutUser()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                else -> {
                }
            }
        }
        if (UserViewModel.user.value !is AuthResource.Authenticated)
            viewModel.getUser()
    }

    private fun setBottomBar() {

        b.bottomBar.apply {
            itemIconTintList = null

            setOnItemSelectedListener { it ->
                when (it.itemId) {

                    R.id.student_upcoming -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.student_frame, UpcomingEventsFragment()).commit()
                    }

                    R.id.student_registrations -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.student_frame, RegisteredFragment()).commit()
                    }

                    R.id.student_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.student_frame, ProfileFragment()).commit()
                    }
                }
                return@setOnItemSelectedListener true
            }

            selectedItemId = R.id.student_upcoming
        }

    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.student_frame)

        if (fragment !is UpcomingEventsFragment) {
            b.bottomBar.selectedItemId = R.id.student_upcoming
        } else
            super.onBackPressed()
    }
}