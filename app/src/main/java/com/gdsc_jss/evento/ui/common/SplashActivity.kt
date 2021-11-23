package com.gdsc_jss.evento.ui.common

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gdsc_jss.evento.databinding.ActivitySplashBinding
import com.gdsc_jss.evento.ui.login.LoginActivity
import com.gdsc_jss.evento.ui.students.StudentActivity
import com.gdsc_jss.evento.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var b: ActivitySplashBinding
    private val viewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(
                    Intent(
                        this,
                        if (viewModel.isLoggedIn()) StudentActivity::class.java
                        else LoginActivity::class.java
                    )
                )
                finish()
            }, 1500
        )
    }
}