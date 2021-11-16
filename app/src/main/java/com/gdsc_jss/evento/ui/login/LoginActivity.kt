package com.gdsc_jss.evento.ui.login


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdsc_jss.evento.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.loginFrame.id, LoginSignUpFragment()).commit()
    }
}