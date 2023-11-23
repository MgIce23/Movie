package com.aps.movie.ui.screen.movie

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.aps.movie.R
import com.aps.movie.ui.components.MoviePlayPresentableSection
import com.aps.movie.ui.theme.spacing
import com.aps.movie.util.isAnyRefreshing
import com.aps.movie.util.refreshAll
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieScreen(
    viewModel: PopularMovieViewModel = hiltViewModel<PopularMovieViewModel>()
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    
    MovieScreenContent(uiState = uiState, scrollState = scrollState)


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

@Composable
fun MovieScreenContent(
    uiState: MovieScreenUiState,
    scrollState: ScrollState
){
    val context = LocalContext.current
    val density = LocalDensity.current

    val upcomingLazyItems = uiState.movieState.upComing.collectAsLazyPagingItems()

    val isRefreshing by derivedStateOf {
        listOf(
            upcomingLazyItems
        ).isAnyRefreshing()
    }

    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val refreshAllPagingData = {
        listOf(
            upcomingLazyItems,
        ).refreshAll()
    }

    LaunchedEffect(isRefreshing) {
        refreshState.isRefreshing = isRefreshing
    }

    SwipeRefresh(
        modifier= Modifier.fillMaxSize(),
        state = refreshState,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = MaterialTheme.spacing.large),
                state = state,
                refreshTriggerDistance = trigger,
                fade = true,
                scale = true,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        },
        onRefresh = refreshAllPagingData) {
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            MoviePlayPresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(R.string.upcoming_movies),
                state = upcomingLazyItems,
                onPresentableClick = {  },
                onMoreClick = {}
            )
        }
        
    }

}

