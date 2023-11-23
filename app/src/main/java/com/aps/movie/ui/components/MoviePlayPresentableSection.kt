package com.aps.movie.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.aps.movie.R
import com.aps.movie.data.modal.Presentable
import com.aps.movie.ui.components.button.MovplayScrollToStartButton
import com.aps.movie.ui.components.items.MoviePresentableItems
import com.aps.movie.ui.components.items.PresentableItemState
import com.aps.movie.ui.theme.spacing
import com.aps.movie.util.isScrollingTowardsStart
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun MoviePlayPresentableSection(
    title: String,
    state: LazyPagingItems<out Presentable>,
    modifier: Modifier = Modifier,
    scrollToBeginningItemsStart: Int = 30,
    showLoadingAtRefresh: Boolean = true,
    showMoreButton: Boolean = true,
    onMoreClick: () -> Unit = {},
    onPresentableClick: (Int) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val isScrollingLeft = listState.isScrollingTowardsStart()

    val scrollToBeginningButton by derivedStateOf {
        val visibleMaxItem = listState.firstVisibleItemIndex > scrollToBeginningItemsStart
        visibleMaxItem && isScrollingLeft
    }

    val onScrollToStartClick: () -> Unit = {
        coroutineScope.launch {
            listState.animateScrollToItem(0)
        }
    }

    val isNotEmpty by derivedStateOf {
        state.run {
            if (showLoadingAtRefresh) {
                loadState.refresh is LoadState.Loading || itemCount > 0
            } else {
                itemCount > 0
            }
        }
    }

    if (isNotEmpty) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                MovPlaySectionLabel(
                    modifier = Modifier.weight(1f),
                    text = title,
                )
                if (showMoreButton) {
                    TextButton(onClick = { onMoreClick() }) {
                        Text(
                            text = stringResource(id = R.string.movies_more),
                            style = MaterialTheme.typography.titleSmall
                        )

                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

            }
            Box {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium)
                ) {
                    items(state) { movie ->
                        movie?.let {
                            MoviePresentableItems(
                                //modifier = Modifier.animateItemPlacement(),
                                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                                presentableState = PresentableItemState.Result(movie),
                                onClick = { onPresentableClick(it.id) }
                            )
                        }
                    }

                    state.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                items(10) {
                                    MoviePresentableItems(presentableState = PresentableItemState.Loading)
                                }
                            }
                            loadState.prepend is LoadState.Loading -> {
                                item{
                                    MoviePresentableItems(presentableState = PresentableItemState.Loading)
                                }
                            }
                        }
                    }
                }
                androidx.compose.animation.AnimatedVisibility(modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = MaterialTheme.spacing.small),
                    visible = scrollToBeginningButton,
                    enter = slideIn(
                        animationSpec = tween(),
                        initialOffset = { size -> IntOffset(-size.width, 0) }),
                    exit = fadeOut(animationSpec = spring()) + scaleOut(
                        animationSpec = spring(),
                        targetScale = 0.3f
                    )
                ) {
                    MovplayScrollToStartButton(
                        onClick = onScrollToStartClick
                    )
                }
            }
        }
    }


}