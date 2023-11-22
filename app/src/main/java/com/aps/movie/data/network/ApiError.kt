package com.aps.movie.data.network

data class ApiError(
    val errorCode: Int,
    val statusCode: Int?,
    val statusMessage: String?
)