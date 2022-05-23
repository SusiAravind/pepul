package com.aravind.pepultask.ui.home

import com.aravind.pepultask.data.repository.PostRepository
import com.aravind.pepultask.ui.base.BaseViewModel
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
class PostViewModel(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val postRepository: PostRepository

) : BaseViewModel(coroutineDispatchers, networkHelper) {


    override fun onCreate() {
    }

}