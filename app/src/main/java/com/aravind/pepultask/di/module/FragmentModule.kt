package com.aravind.pepultask.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aravind.pepultask.data.repository.PostRepository
import com.aravind.pepultask.ui.base.BaseFragment
import com.aravind.pepultask.ui.main.MainSharedViewModel
import com.aravind.pepultask.ui.post.PostViewModel
import com.aravind.pepultask.utils.ViewModelProviderFactory
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import com.mindorks.paracamera.Camera
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)



    @Provides
    fun provideCamera() = Camera.Builder()
        .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
        .setTakePhotoRequestCode(1)
        .setDirectory("temp")
        .setName("camera_temp_img")
        .setImageFormat(Camera.IMAGE_JPEG)
        .setCompression(75)
        .setImageHeight(500)// it will try to achieve this height as close as possible maintaining the aspect ratio;
        .build(fragment)

    @Provides
    fun provideMainSharedViewModel(
        coroutineDispatchers: CoroutineDispatchers,
        networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProvider(
        fragment.activity!!, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(coroutineDispatchers, networkHelper)
        }).get(MainSharedViewModel::class.java)

    @Provides
    fun providePostViewModel(
        coroutineDispatchers: CoroutineDispatchers,
        networkHelper: NetworkHelper,
        postRepository: PostRepository
    ): PostViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(PostViewModel::class) {
            PostViewModel(coroutineDispatchers, networkHelper,postRepository
            )
        }).get(PostViewModel::class.java)

}