package com.aps.movie.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl =
            chain.request().url.newBuilder().addQueryParameter("api_key", "BuildConfig.API_KEY").build()
        return chain.proceed(chain.request().newBuilder().url(newUrl).build())
    }
}