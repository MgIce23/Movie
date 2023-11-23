package com.aps.movie.ui.components.items

import android.util.Log
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.aps.movie.data.modal.Presentable
import com.aps.movie.ui.theme.spacing
import com.aps.movie.util.ImageUrlParser
import com.aps.movie.util.getMaxSizeInt
import com.aps.movie.util.rememberTmdbImagePainter

@Composable
fun MovResultPresentableItem(
    presentable: Presentable,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true,
    onClick : (() -> Unit)? = null
){

    val hasPoster by derivedStateOf {
        presentable.posterPath != null
    }

    Box(
        modifier = modifier
            .clickable(
                enabled = false,
                onClick = {onClick?.invoke()}
            )
    ){
        if(hasPoster){
            MovImageSetting(modifier = Modifier.matchParentSize(),
                imagePath = presentable.posterPath,
                imageType = ImageUrlParser.ImageType.Poster){
                size(coil.size.Size.ORIGINAL)
                scale(Scale.FILL)
                crossfade(true)
            }
        }else{
            MovplayNoPhotoPresentableItem(
                modifier = Modifier.fillMaxSize()
            )
        }

        if (showTitle) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(MaterialTheme.spacing.extraSmall),
                    text = presentable.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }

    }

}

@Composable
fun MovImageSetting(
    imagePath : String?,
    imageType: ImageUrlParser.ImageType,
    modifier: Modifier = Modifier,
    strategy: ImageUrlParser.MatchingStrategy = ImageUrlParser.MatchingStrategy.FirstBiggerWidth,
    contentScale: ContentScale = ContentScale.FillBounds,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
){
    BoxWithConstraints(modifier = modifier) {
        val (maxWidth,maxHeight) = getMaxSizeInt()
        val painter = rememberTmdbImagePainter(path = imagePath, type = imageType, preferredSize = Size(maxWidth,maxHeight),
        strategy){
            builder()
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
            .data("https://image.tmdb.org/t/p/original$imagePath")
            .build(), contentDescription = null)
    }
}