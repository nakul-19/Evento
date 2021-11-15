package com.gdsc_jss.evento.ui.common

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gdsc_jss.evento.databinding.ActivitySplashBinding
import com.gdsc_jss.evento.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var b: ActivitySplashBinding
    @Inject
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)


        Handler().postDelayed(
            {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 1500
        )
    }
}