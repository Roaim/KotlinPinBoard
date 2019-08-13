package com.roaim.kotlinpinboard.data.model

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose

data class Pin(
    val urls: Urls? = null,
    val currentUserCollections: List<Any?>? = null,
    val color: String? = null,
    val width: Int? = null,
    val createdAt: String? = null,
    val links: Links? = null,
    val id: String? = null,
    val categories: List<CategoriesItem?>? = null,
    val likedByUser: Boolean? = null,
    val user: User? = null,
    val height: Int? = null,
    val likes: Int? = null,
    @Expose val thumb: MutableLiveData<Bitmap> = MutableLiveData(),
    @Expose val progress: MutableLiveData<Int> = MutableLiveData()
)