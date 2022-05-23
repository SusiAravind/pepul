package com.aravind.pepultask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aravind.pepultask.R
import com.aravind.pepultask.data.model.Post
import com.aravind.pepultask.data.repository.PostRepository
import com.aravind.pepultask.ui.base.BaseViewModel
import com.aravind.pepultask.utils.common.Resource
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.rx.CoroutineDispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper,
    private val postRepository: PostRepository,
    private val allPostList: ArrayList<Post>
) : BaseViewModel(coroutineDispatchers, networkHelper) {


    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>> = _posts

    private val _refreshPosts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val refreshPosts: LiveData<Resource<List<Post>>> = _refreshPosts

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading


    private val _paginator: MutableSharedFlow<Pair<String?, String?>> =
        MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )


    private var lastFetchId: String? = null

    init {
        _paginator
            .onEach {
                onFetchHomePostList()
            }
            .catch {
                messageStringId.postValue(Resource.error(R.string.try_again))
            }.launchIn(viewModelScope)

    }

    private fun onFetchHomePostList() {
        viewModelScope.launch(coroutineDispatchers.io()) {

            try {
                postRepository.fetchPostList(lastFetchId)
                    .onStart { _loading.postValue(true) }
                    .collect {
                        _loading.postValue(false)
                        it.data?.let { it1 -> allPostList.addAll(it1.toMutableList()) }
                        lastFetchId = allPostList.minByOrNull { post -> post.id }?.id
                        _posts.postValue(it)
                    }

            } catch (ex: Exception) {
                _loading.postValue(false)
                handleNetworkError(ex)
            }
        }

    }

    override fun onCreate() {
        loadMorePosts()
    }

    private fun loadMorePosts() {
        _paginator.tryEmit(Pair(lastFetchId,lastFetchId))
    }

    fun onLoadMore() {
        if (loading.value == false) loadMorePosts()
    }

    fun onNewPost(post: Post) {
        allPostList.add(0, post)
        _refreshPosts.postValue(Resource.success(mutableListOf<Post>().apply { addAll(allPostList) }))
    }

    private fun onDeletePost(post: Post) {
        allPostList.remove(post)
        _refreshPosts.postValue(Resource.success(mutableListOf<Post>().apply { addAll(allPostList) }))
    }

    fun deleteUserPost(post: Post) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutineDispatchers.io()) {
                try {
                    postRepository.deletePost(postId = post.id)
                        .collect {
                            onDeletePost(post)
                        }
                } catch (ex: Exception) {
                    handleNetworkError(ex)
                }
            }
        }
    }
}