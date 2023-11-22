package com.aps.movie.data.modal

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
    @Json(name = "images")
    val imagesConfig: ImagesConfig,
    @Json(name = "change_keys")
    val changeKeys: List<String>
)
