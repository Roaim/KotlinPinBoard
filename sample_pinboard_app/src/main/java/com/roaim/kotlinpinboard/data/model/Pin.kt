package com.roaim.kotlinpinboard.data.model

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
    val likes: Int? = null
)