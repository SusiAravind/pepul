package com.aravind.pepultask.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Post(
    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("file")
    val file: String,

    @Expose
    @SerializedName("file_type")
    val file_type: String


)