package com.aravind.pepultask.ui.main

import android.os.Bundle
import android.view.View
import com.aravind.pepultask.databinding.ActivityMainBinding
import com.aravind.pepultask.di.component.ActivityComponent
import com.aravind.pepultask.ui.base.BaseActivity
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding: ActivityMainBinding


    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel


    override fun provideLayoutId(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {


    }


}
