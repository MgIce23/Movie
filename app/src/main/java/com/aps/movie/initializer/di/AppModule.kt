package com.aps.movie.initializer.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.aps.movie.domain.MovieIdJsonAdapter
import com.aps.movie.data.network.ApiService
import com.aps.movie.data.network.AuthInterceptor
import com.aps.movie.util.AppConstant.NETWORK_TIMEOUT
import com.aps.movie.util.NetworkStatusTracker
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExternalCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @Singleton
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun provideNetworkStatusTracker(@ApplicationContext context: Context) = NetworkStatusTracker(context)

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("img_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .okHttpClient {
                OkHttpClient.Builder().build()
            }
            .build()
    }


   /* @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY})
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .client(client)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(MovieIdJsonAdapter())
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .build()*/

}