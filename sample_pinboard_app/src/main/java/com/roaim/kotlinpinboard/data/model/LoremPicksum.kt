package com.roaim.kotlinpinboard.data.model

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose

data class LoremPicksum(
	val author: String? = null,
	val width: Int? = null,
	val downloadUrl: String? = null,
	val id: String? = null,
	val url: String? = null,
	val height: Int? = null,
	@Expose val thumb: MutableLiveData<Bitmap> = MutableLiveData(),
	@Expose val progress: MutableLiveData<Int> = MutableLiveData()
)
