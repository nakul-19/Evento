package com.gdsc_jss.evento.ui.students

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdsc_jss.evento.databinding.ActivitySplashBinding

class StudentActivity : AppCompatActivity() {

    private  lateinit var b: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)
    }
}