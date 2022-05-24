package com.aravind.pepultask.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aravind.pepultask.data.remote.NetworkService
import com.aravind.pepultask.data.repository.PostRepository
import com.aravind.pepultask.ui.base.BaseActivity
import com.aravind.pepultask.ui.main.MainSharedViewModel
import com.aravind.pepultask.ui.main.MainViewModel
import com.aravind.pepultask.utils.ViewModelProviderFactory
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    fun provideMainViewModel(
        coroutineDispatchers: CoroutineDispatchers,
        networkHelper: NetworkHelper,
        postRepository: PostRepository
    ): MainViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(coroutineDispatchers, networkHelper, postRepository,ArrayList())
        }).get(MainViewModel::class.java)


    @Provides
    fun provideMainSharedViewModel(
        coroutineDispatchers: CoroutineDispatchers,
        networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(coroutineDispatchers, networkHelper)
        }).get(MainSharedViewModel::class.java)
}