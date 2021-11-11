package com.gdsc_jss.evento

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Evento: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())
        }
    }
}