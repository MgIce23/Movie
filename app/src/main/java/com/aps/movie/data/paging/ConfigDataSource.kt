package com.aps.movie.data.paging

import android.content.Context
import android.util.Log
import com.aps.movie.data.modal.Config
import com.aps.movie.data.network.movie.MovieApiHelper
import com.aps.movie.data.network.onException
import com.aps.movie.data.network.onSuccess
import com.aps.movie.data.network.request
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val apiMovieApiHelper: MovieApiHelper
){
    private val _config: MutableStateFlow<Config?> = MutableStateFlow(null)

    init {
        apiMovieApiHelper.getConfig().request{ response ->
            response.onSuccess {
                externalScope.launch(defaultDispatcher){
                    val config = data
                    _config.emit(config)
                }
            }.onException {
                Log.d("exception", exception.message.toString())
            }
        }
    }
}