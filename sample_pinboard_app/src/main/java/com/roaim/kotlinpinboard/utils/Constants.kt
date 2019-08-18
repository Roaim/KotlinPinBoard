package com.roaim.kotlinpinboard.utils

object Constants {
    fun getImageListUrl(page : Int = 1, limit : Int = 10) = "https://picsum.photos/v2/list?page=$page&limit=$limit"
    fun getThumbUrl(id: String, width: Int = 360, height: Int = 480) = "https://picsum.photos/id/$id/$width/$height"
    fun getImageUrl(id: String, width : Int = 1080, height : Int = 1920) = "https://picsum.photos/id/$id/$width/$height"
}