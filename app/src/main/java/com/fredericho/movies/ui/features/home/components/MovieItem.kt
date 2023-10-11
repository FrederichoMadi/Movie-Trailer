package com.fredericho.movies.ui.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    image: String,
    title: String,
    isFavorite: Boolean,
    insertFavoriteMovie: () -> Unit,
    navigationToDetail: () -> Unit,
) {
    Box(modifier = Modifier) {
        Column(modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigationToDetail()
            }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data("$IMAGE_URL$image")
                    .placeholder(R.drawable.ic_launcher_foreground).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }

        IconButton(
            onClick = insertFavoriteMovie,
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .size(40.dp)
                .align(Alignment.TopEnd),
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
            enabled = true,
        ) {
            Icon(
                if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "",
                tint = Color.Red
            )
        }

    }
}