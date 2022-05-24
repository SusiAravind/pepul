package com.aravind.pepultask.ui.main

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.aravind.pepultask.R
import com.aravind.pepultask.databinding.ActivityMainBinding
import com.aravind.pepultask.di.component.ActivityComponent
import com.aravind.pepultask.ui.base.BaseActivity
import com.aravind.pepultask.utils.common.Status
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding: ActivityMainBinding
var count:Int=0;
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
                        count=it.data.size
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




        viewModel.refreshPosts.observe(this) {
            it.data?.run {
                pagerAdapter.updateData(this)
               binding.viewPager.setCurrentItem(0,true)
            }
        }

        pagerAdapter=PagerAdapter(supportFragmentManager, ArrayList())
        viewModel.posts.observe(this) {
            if (it.data?.isNotEmpty() == true) {
                count=it.data.size
                pagerAdapter.updateData(it.data.toMutableList())
            }
        }
        binding.viewPager.adapter = pagerAdapter
    }

    override fun setupView(savedInstanceState: Bundle?) {

        binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position==count-2){
                    viewModel.onLoadMore()
                }

            }
        })


    }

    public fun deletePost(pos:Int){
        viewModel.posts.value?.data?.get(pos)?.let { viewModel.deleteUserPost(it) }
        binding.viewPager.setCurrentItem(pos-1,true)
        pagerAdapter.removeData(pos)
    }

}
