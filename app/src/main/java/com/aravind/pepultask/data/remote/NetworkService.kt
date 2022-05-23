package com.aravind.pepultask.data.remote

import com.aravind.pepultask.data.remote.response.GeneralResponse
import com.aravind.pepultask.data.remote.response.PostListResponse
import okhttp3.MultipartBody
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(Endpoints.ALL_POST_LIST)
    suspend fun doHomePostListCall(
        @Query("lastFetchId") lastFetchId: String?,
    ): PostListResponse

  @GET(Endpoints.DELETE_POST)
    suspend fun doPostDeleteCall(
        @Query("id") id: String?,
    ): GeneralResponse



    @Multipart
    @POST(Endpoints.IMAGE_UPLOAD)
    suspend fun doImageUpload(
        @Part image: MultipartBody.Part,
    ): GeneralResponse

}