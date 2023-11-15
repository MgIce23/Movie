package com.aps.movie.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aps.movie.domain.data.Movie

@Composable
fun PopularMovieScreen(viewModel: PopularMovieViewModel = hiltViewModel<PopularMovieViewModel>()){

    val moviePagingItems: LazyPagingItems<Movie> =
        viewModel.loadPopularViewModel().collectAsLazyPagingItems()

     Box(modifier = Modifier.fillMaxSize(),
         contentAlignment = Alignment.Center){
         Text(text = moviePagingItems.itemCount.toString())
     }

}