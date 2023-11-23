package com.aps.movie.ui.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.aps.movie.data.modal.Presentable
import com.aps.movie.ui.components.others.MovieErrorPresentableItem
import com.aps.movie.ui.components.others.MovieLoadingPresentableItem
import com.aps.movie.ui.theme.Size
import com.aps.movie.ui.theme.sizes
import com.aps.movie.ui.theme.spacing

@Composable
fun MoviePresentableItems(
    presentableState: PresentableItemState,
    modifier: Modifier = Modifier,
    size: Size = MaterialTheme.sizes.presentableItemSmall,
    selected: Boolean = false,
    showTitle: Boolean = false,
    transformations: GraphicsLayerScope.() -> Unit = {},
    onClick: (() -> Unit)? = null
){
    Card(
        modifier = modifier
            .width(size.width)
            .aspectRatio(size.ratio)
            .graphicsLayer {
                transformations()
            },
        shape = MaterialTheme.shapes.medium,
        border = if(selected) BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary) else null
    ) {
        when(presentableState){
            is PresentableItemState.Loading -> {
                MovieLoadingPresentableItem(modifier = Modifier.fillMaxSize())}
            is PresentableItemState.Error -> {
                MovieErrorPresentableItem(modifier = Modifier.fillMaxSize())
            }
            is PresentableItemState.Result -> {
                MovResultPresentableItem(
                    modifier = Modifier.fillMaxSize(),
                    presentable = presentableState.presentable,
                    showTitle = showTitle,
                    onClick = onClick
                )
            }
        }
    }

}


sealed class PresentableItemState{
    object Loading : PresentableItemState()
    object Error : PresentableItemState()
    data class Result(val presentable:Presentable) : PresentableItemState()
}