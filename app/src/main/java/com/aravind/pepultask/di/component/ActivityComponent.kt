package com.aravind.pepultask.di.component

import com.aravind.pepultask.di.ActivityScope
import com.aravind.pepultask.di.module.ActivityModule
import com.aravind.pepultask.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {


    fun inject(activity: MainActivity)
}