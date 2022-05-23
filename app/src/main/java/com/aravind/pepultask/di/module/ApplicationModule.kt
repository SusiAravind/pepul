package com.aravind.pepultask.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.aravind.pepultask.BuildConfig
import com.aravind.pepultask.PepulTaskApplication
import com.aravind.pepultask.data.local.db.DatabaseService
import com.aravind.pepultask.data.local.db.dao.PostDao
import com.aravind.pepultask.data.remote.NetworkService
import com.aravind.pepultask.data.remote.Networking
import com.aravind.pepultask.di.ApplicationContext
import com.aravind.pepultask.di.TempDirectory
import com.aravind.pepultask.utils.common.Constants
import com.aravind.pepultask.utils.common.FileUtils
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import com.aravind.pepultask.utils.rx.CoroutineDispatchersProvider
import com.bumptech.glide.util.ViewPreloadSizeProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: PepulTaskApplication) {


    @Singleton
    @Provides
    fun provideApplication(): Application = application


    @Singleton
    @ApplicationContext
    @Provides
    fun provideContext(): Context = application

    @Provides
    @Singleton
    @TempDirectory
    fun provideTempDirectory() = FileUtils.getDirectory(application, "temp")

    @Provides
    fun provideSchedulerProvider(): CoroutineDispatchers = CoroutineDispatchersProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        application.getSharedPreferences("pepul_task", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(
            application, DatabaseService::class.java,
            "pepul_task"
        ).build()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    @Singleton
    fun providePostDao(
        database: DatabaseService
    ): PostDao = database.postDao()

    @Provides
    @Singleton
    fun provideViewPreloadSizeProvider(): ViewPreloadSizeProvider<String> =
        ViewPreloadSizeProvider<String>()


}