package com.aps.movie.ui.screen.movie

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieScreen(
    viewModel: PopularMovieViewModel = hiltViewModel<PopularMovieViewModel>()
){

   /* Scaffold(topBar = {

        TopAppBar(
            title = { Text(text = "Popular Movie List", color = Color.White) },
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color(0xff4a4ae8))
        )

    }) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {

            val moviePagingItems: LazyPagingItems<MovieResponse> = viewModel.loadPopularMovie().collectAsLazyPagingItems()
            val upComingPagingItems = viewModel.loadUpcomingMovie().collectAsLazyPagingItems()

            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(currentLineSpan = 2)}){
                    LazyRow(modifier = Modifier.padding(16.dp).fillMaxWidth()
                    ){
                        items(moviePagingItems.itemCount){ index ->
                            val item = upComingPagingItems[index]!!
                            MovieItem(movie = item)
                        }

                        upComingPagingItems.apply {
                            when{
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        LoadingIndicator()
                                    }}
                                loadState.refresh is LoadState.Error ->{
                                    val error = upComingPagingItems.loadState.refresh as LoadState.Error
                                    item{
                                        ErrorMessageItem(
                                            errorMessage = error.error.message ?: "Error!",
                                            onClickRetry = {
                                                retry()
                                            })
                                    }
                                }

                                loadState.append is LoadState.Loading ->{
                                    item { LoadingIndicator() }
                                }
                                loadState.append is LoadState.Error ->{
                                    val error = moviePagingItems.loadState.append as LoadState.Error
                                    item {
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
*/


}

