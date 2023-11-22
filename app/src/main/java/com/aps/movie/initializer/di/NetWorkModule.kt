package com.aps.movie.initializer.di

import android.content.Context
import com.aps.movie.data.network.AuthInterceptor
import com.aps.movie.data.network.movie.MovieApi
import com.aps.movie.data.network.movie.MovieApiHelper
import com.aps.movie.data.network.movie.MovieApiHelperImpl
import com.aps.movie.data.network.remote.ApiParam
import com.aps.movie.data.network.remote.DateJsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton
import kotlin.time.toJavaDuration

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context:Context) = Cache(context.cacheDir,ApiParam.cacheSize)

    @Provides
    @Singleton
    fun provideAuthenticationInterceptor() : Interceptor{
        return Interceptor { chain ->
            val request = chain.request()
            val requestUrl = request.url
            val url = requestUrl.newBuilder()
                .addQueryParameter("api_key","")
                .build()

            val modifierResult = request.newBuilder()
                .url(url)
                .build()
            chain.proceed(modifierResult)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
        authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().apply{
        if (true) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(loggingInterceptor)
        }
    }
        .addInterceptor(authInterceptor)
        .cache(cache)
        .connectTimeout(ApiParam.Timeouts.connect.toJavaDuration())
        .writeTimeout(ApiParam.Timeouts.write.toJavaDuration())
        .readTimeout(ApiParam.Timeouts.read.toJavaDuration())
        .build()


    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(Date::class.java,DateJsonAdapter().nullSafe()).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,moshi: Moshi) =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(ApiParam.secureBaseUrl.toHttpUrl())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit) : MovieApi = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideMovieApiHelper(apiHelper: MovieApiHelperImpl) : MovieApiHelper = apiHelper











   /* @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val collector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(collector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()
    }*/

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

}