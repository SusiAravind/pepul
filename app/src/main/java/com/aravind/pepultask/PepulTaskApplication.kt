package com.aravind.pepultask

import android.app.Application
import android.content.SharedPreferences
import com.aravind.pepultask.di.component.ApplicationComponent
import com.aravind.pepultask.di.component.DaggerApplicationComponent
import com.aravind.pepultask.di.module.ApplicationModule
import com.aravind.pepultask.utils.ThemeManager
import javax.inject.Inject

class PepulTaskApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var preferences : SharedPreferences

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        initTheme()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    // Needed to replace the component with a test specific one
    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }

    private fun initTheme() {
        preferences.getString("PREF_THEME_MODE","Default")?.let { ThemeManager.applyTheme(it) }
    }
}