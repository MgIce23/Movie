package com.aps.movie.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aps.movie.domain.MovieResponse
import com.aps.movie.domain.data.Movie
import com.aps.movie.network.ApiService
import javax.inject.Inject

class PopularMoviePagingSource @Inject constructor(private val apiService: ApiService): PagingSource<Int,Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try{
            val pageIndex = params.key ?: 1
            val movies = apiService.getPopularMovie(pageIndex)

            val prevKey = if (pageIndex == 1) null else pageIndex - 1

            val nextKey = if (movies.results.isEmpty()) null else pageIndex + 1

            return LoadResult.Page(
                data = movies.results.map(MovieResponse::mapToMovie),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (t: Throwable){
            LoadResult.Error(t)
        }
    }

}