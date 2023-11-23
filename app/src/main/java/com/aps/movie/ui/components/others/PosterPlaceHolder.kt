package com.aps.movie.ui.components.others

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aps.movie.ui.theme.sizes
import com.aps.movie.util.defaultPlaceHolder

@Composable
fun PosterPlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.defaultPlaceHolder()
    )
}

@Composable
fun MovieLoadingPresentableItem(modifier: Modifier = Modifier) {
    PosterPlaceHolder(modifier = modifier)
}

@Composable
fun MovieErrorPresentableItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .border(width = 1.dp, color = Color.White, shape = MaterialTheme.shapes.medium)
    ) {
        Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
    }
}