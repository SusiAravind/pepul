package com.aravind.pepultask.data.repository

import com.aravind.pepultask.data.local.db.dao.PostDao
import com.aravind.pepultask.data.local.db.entity.PostEntity
import com.aravind.pepultask.data.model.Post
import com.aravind.pepultask.data.remote.NetworkService
import com.aravind.pepultask.data.remote.response.GeneralResponse
import com.aravind.pepultask.utils.common.Resource
import com.aravind.pepultask.utils.log.Logger
import com.aravind.pepultask.utils.network.NetworkHelper
import com.aravind.pepultask.utils.network.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val networkService: NetworkService,
    private val networkHelper: NetworkHelper,
    private val postDao: PostDao
) {

    companion object {
        const val TAG = "PostRepository"
    }

    fun fetchPostList(lastFetchId: String?): Flow<Resource<List<Post>>> {

        return networkBoundResource(
            query = { postDao.getAll().map { processData(it) } },
            fetch = { networkService.doHomePostListCall(lastFetchId)},
            saveFetchResult = { apiResponse ->
                if (apiResponse.statusCode == "success" && apiResponse.data.isNotEmpty()) {
                    postDao.preparePostAndCreator(apiResponse.data)
                }
            },
            shouldFetch = { networkHelper.isNetworkConnected() },
            coroutineDispatcher = Dispatchers.IO
        )

    }


  /*  fun fetchUserPostList(user: User) : Flow<List<Post>> {

        return flow {
            val api = networkService.doMyPostsCall(user.id,user.accessToken)
            emit(api.data)
        }.flowOn(Dispatchers.IO)
    }


    fun deleteUserPost(postId: String, user: User) : Flow<GeneralResponse> {
        return flow {
            val api = networkService.doPostDelete(postId,user.id,user.accessToken)
            emit(api)
        }.flowOn(Dispatchers.IO)
    }*/

    fun deletePost(postId: String) : Flow<GeneralResponse> {
        return flow {
            val api = networkService.doPostDeleteCall(postId)
            emit(api)
        }.flowOn(Dispatchers.IO)
    }


    private fun processData(listItems : List<PostEntity>) : List<Post>{
        val arrayList = mutableListOf<Post>()
        return arrayList.toList()
    }

}