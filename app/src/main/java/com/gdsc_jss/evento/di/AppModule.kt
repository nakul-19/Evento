package com.gdsc_jss.evento.di

import android.content.Context
import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.network.ApiInterface
import com.gdsc_jss.evento.network.repositories.EventRepository
import com.gdsc_jss.evento.network.repositories.LoginRepository
import com.gdsc_jss.evento.network.repositories.UserRepository
import com.gdsc_jss.evento.util.baseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        val client = OkHttpClient.Builder().connectTimeout(0, TimeUnit.SECONDS).readTimeout(
            0,
            TimeUnit.SECONDS
        ).writeTimeout(0, TimeUnit.SECONDS).addNetworkInterceptor(StethoInterceptor())

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(api: ApiInterface): LoginRepository = LoginRepository(api)

    @Provides
    @Singleton
    fun provideEventRepository(api: ApiInterface): EventRepository = EventRepository(api)

    @Provides
    @Singleton
    fun provideUserRepository(api: ApiInterface, sp: SharedPreferences): UserRepository = UserRepository(api,sp)

}