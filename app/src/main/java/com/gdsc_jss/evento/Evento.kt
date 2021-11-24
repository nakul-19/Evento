package com.gdsc_jss.evento

import android.app.Application
import com.cloudinary.android.MediaManager
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gdsc_jss.evento.ui.login.LoginActivity
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Evento : Application() {

    override fun onCreate() {
        super.onCreate()

        val config: HashMap<String, String> = HashMap()
        config["cloud_name"] = "abhistrike"
        config["api_key"] = "152295545861796"
        config["api_secret"] = "tbrjkMsTA2HEr9PBnRMk7MXM-f8"
        MediaManager.init(this, config);


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}