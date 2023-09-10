package com.fredericho.movies.ui.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fredericho.movies.R
import com.fredericho.movies.util.IMAGE_URL

@Composable
fun MovieItem(
    image : String,
    title : String,
    navigationToDetail: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigationToDetail()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$IMAGE_URL$image")
                .placeholder(R.drawable.ic_launcher_foreground)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            )
        Text(
            text = title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            )
    }
}