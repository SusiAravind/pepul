package com.aravind.pepultask.ui.main

import android.os.Bundle
import android.view.View
import com.aravind.pepultask.R
import com.aravind.pepultask.databinding.ActivityMainBinding
import com.aravind.pepultask.di.component.ActivityComponent
import com.aravind.pepultask.ui.base.BaseActivity
import com.aravind.pepultask.utils.common.Status
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding: ActivityMainBinding

lateinit var pagerAdapter:PagerAdapter;
    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel


    override fun provideLayoutId(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupObservers() {
        super.setupObservers()

        viewModel.posts.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.isNotEmpty() == true) {
                        pagerAdapter.appendData(it.data.toMutableList())
                    }
                }
                Status.ERROR -> {
                    showMessage(getString(R.string.server_connection_error))
                }
                else -> {

                }
            }
        }


    }

    override fun setupView(savedInstanceState: Bundle?) {

        pagerAdapter=PagerAdapter(supportFragmentManager, ArrayList())
        viewModel.posts.observe(this) {
            if (it.data?.isNotEmpty() == true) {
                pagerAdapter.updateData(it.data.toMutableList())
             }
        }
        binding.viewPager.adapter = pagerAdapter
    }


}
