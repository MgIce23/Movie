package com.aps.movie.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp),
            color = Color(0xff4a4ae8)
        )
    }
}

@Composable
fun ErrorMessageItem(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onClickRetry: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            modifier = Modifier
                .weight(2f)
                .padding(4.dp),
            text = errorMessage,
            color = Color.Red,
            fontSize = 14.sp
        )

        Button(
            modifier = Modifier.weight(1f),
            onClick = { onClickRetry() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Retry", modifier = Modifier.padding(4.dp), fontSize = 14.sp)
        }
    }

}