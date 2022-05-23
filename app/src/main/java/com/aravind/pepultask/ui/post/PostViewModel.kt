package com.aravind.pepultask.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravind.pepultask.data.model.Post
import com.aravind.pepultask.data.repository.PostRepository
import com.aravind.pepultask.ui.base.BaseViewModel
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class PostViewModel(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val postRepository: PostRepository

) : BaseViewModel(coroutineDispatchers, networkHelper) {
    val userPosts : MutableLiveData<List<Post>> = MutableLiveData()

    override fun onCreate() {
    }

}