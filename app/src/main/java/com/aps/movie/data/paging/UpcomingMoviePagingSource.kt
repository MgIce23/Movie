package com.aps.movie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aps.movie.domain.MovieResponse
import com.aps.movie.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpcomingMoviePagingSource @Inject constructor(private val apiService: ApiService): PagingSource<Int,MovieResponse>(){
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try{
            val pageIndex = params.key ?: 1
            val movies = apiService.getUpcomingMovies(pageIndex)

            val prevKey = if (pageIndex == 1) null else pageIndex - 1
            val nextKey = if (movies.results.isEmpty()) null else pageIndex + 1
            withContext(Dispatchers.IO){
                LoadResult.Page(
                    data = movies.results,
                    prevKey = prevKey,
                    nextKey = nextKey
                )

            }

        } catch (t: Throwable){
            return LoadResult.Error(t)
        }
    }

}