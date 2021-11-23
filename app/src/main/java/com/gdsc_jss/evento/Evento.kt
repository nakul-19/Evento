package com.gdsc_jss.evento

import android.app.Application
import android.content.Intent
import com.gdsc_jss.evento.ui.login.LoginActivity
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Evento : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())
        }
    }
}