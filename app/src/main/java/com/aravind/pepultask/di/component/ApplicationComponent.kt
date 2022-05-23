package com.aravind.pepultask.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.aravind.pepultask.PepulTaskApplication
import com.aravind.pepultask.data.local.db.DatabaseService
import com.aravind.pepultask.data.local.db.dao.PostDao
import com.aravind.pepultask.data.remote.NetworkService
import com.aravind.pepultask.di.ApplicationContext
import com.aravind.pepultask.di.TempDirectory
import com.aravind.pepultask.di.module.ApplicationModule
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import dagger.Component
import java.io.File
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: PepulTaskApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getSharedPreferences(): SharedPreferences

    fun getNetworkHelper(): NetworkHelper

    fun getSchedulerProvider(): CoroutineDispatchers

    fun getPostDao(): PostDao

    @TempDirectory
    fun getTempDirectory(): File

    fun getViewPreloadSizeProvider() : ViewPreloadSizeProvider<String>

}