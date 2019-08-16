package com.roaim.kotlinpinboard.utils

object Constants {
    fun getImageListUrl(page : Int = 1, limit : Int = 10) = "https://picsum.photos/v2/list?page=$page&limit=$limit"
    fun getThumbUrl(id: String) = "https://picsum.photos/id/$id/240/320"
    fun getImageUrl(id: String) = "https://picsum.photos/id/$id/1080/1920"
}