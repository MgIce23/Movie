package com.aps.movie.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl =
            chain.request().url.newBuilder().addQueryParameter("api_key", "f29950b90a97d6ced65821a923e55012").build()
        return chain.proceed(chain.request().newBuilder().url(newUrl).build())
    }
}