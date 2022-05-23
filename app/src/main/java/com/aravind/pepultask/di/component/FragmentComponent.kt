package com.aravind.pepultask.di.component

import com.aravind.pepultask.di.FragmentScope
import com.aravind.pepultask.di.module.FragmentModule
import com.aravind.pepultask.ui.post.PostFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {


    fun inject(fragment: PostFragment)


}