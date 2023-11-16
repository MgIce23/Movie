package com.aps.movie.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aps.movie.domain.MovieResponse
import com.aps.movie.domain.data.Movie
import com.aps.movie.presentation.components.ErrorMessageItem
import com.aps.movie.presentation.components.LoadingIndicator
import com.aps.movie.presentation.components.MovieItem
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieScreen(viewModel: PopularMovieViewModel = hiltViewModel<PopularMovieViewModel>()){

    Scaffold(topBar = {

        TopAppBar(
            title = { Text(text = "Popular Movie List", color = Color.White) },
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color(0xff4a4ae8))
        )

    }) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {

            val moviePagingItems: LazyPagingItems<MovieResponse> = viewModel.loadPopularMovie().collectAsLazyPagingItems()

            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(moviePagingItems.itemCount) { index ->
                    val item = moviePagingItems[index]!!
                    MovieItem(movie = item)
                }

                moviePagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                LoadingIndicator()
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = moviePagingItems.loadState.refresh as LoadState.Error

                            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                ErrorMessageItem(
                                    errorMessage = error.error.message ?: "Error!",
                                    onClickRetry = {
                                        retry()
                                    })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                LoadingIndicator()
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = moviePagingItems.loadState.append as LoadState.Error
                            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) {
                                ErrorMessageItem(
                                    errorMessage = error.error.message ?: "Error!",
                                    onClickRetry = {
                                        retry()
                                    })
                            }
                        }
                    }
                }


            }
        }

    }



}

