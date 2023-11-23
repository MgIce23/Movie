package com.aps.movie.util

import android.util.Size
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aps.movie.LocalImageUrlParser
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun LazyListState.isScrollingTowardsStart(): Boolean{
    var previousIndex by remember(this){
        mutableIntStateOf(firstVisibleItemIndex)
    }

    var previousScrollOffset by remember(this){
        mutableIntStateOf(firstVisibleItemScrollOffset)
    }

    return remember(this){
        derivedStateOf {
            if(previousIndex != firstVisibleItemIndex){
                previousIndex > firstVisibleItemIndex
            }
            else {
                previousScrollOffset>= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }.value
    }
}


fun Modifier.defaultPlaceHolder() = composed{
    placeholder(
        visible = true,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(durationMillis = 500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    )

}

@Composable
fun BoxWithConstraintsScope.getMaxSizeInt(): Pair<Int, Int> {
    return LocalDensity.current.run { maxWidth.toPx().toInt() to maxHeight.toPx().toInt() }
}

@Composable
inline fun rememberTmdbImagePainter(
    path: String?,
    type: ImageUrlParser.ImageType,
    preferredSize: Size,
    strategy: ImageUrlParser.MatchingStrategy = ImageUrlParser.MatchingStrategy.FirstBiggerWidth,
    builder: ImageRequest.Builder.() -> Unit = {},
): AsyncImagePainter {
    val imageUrlParser = LocalImageUrlParser.current

    val imageUrl = imageUrlParser?.getImageUrl(
        path = path,
        type = type,
        preferredSize = preferredSize,
        strategy = strategy
    )

    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .apply {
                builder(this)
            }.build()
    )
}

fun Iterable<LazyPagingItems<*>>.isAnyRefreshing(): Boolean {
    return any { it.itemCount > 0 && it.loadState.refresh is LoadState.Loading }
}

fun Iterable<LazyPagingItems<*>>.refreshAll() {
    return forEach { it.refresh() }
}

