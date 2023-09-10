package com.fredericho.movies.ui.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.fredericho.movies.R
import com.fredericho.movies.core.movie.api.model.Cast
import com.fredericho.movies.core.movie.api.model.GenresItem
import com.fredericho.movies.ui.theme.Navy
import com.fredericho.movies.util.IMAGE_URL
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun DetailScreen(
    viewModel: DetailMovieViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    id: Int,
) {
    LaunchedEffect(Unit) {
        viewModel.getDetailMovie(id)
        viewModel.getCreditMovie(id)
        viewModel.getVideoMovie(id)
    }

    val state by viewModel.detailState.collectAsState()
    val movie = state.movie

    val creditState by viewModel.creditState.collectAsState()
    val credit = creditState.credit

    val videoState by viewModel.videoState.collectAsState()
    val video = videoState.videos

    val context = LocalContext.current

    if (state.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Navy)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            if (video.isNotEmpty()) {
                AndroidView(factory = {
                    YouTubePlayerView(context).apply {
                        this.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.cueVideo(video.first().key, 0F)
                            }
                        })

                    }
                },
                    modifier = Modifier.fillMaxHeight(0.3f))
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart),
                onClick = { navigateToBack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 24.dp))
        HeaderTitle(movie.originalTitle, movie.runtime, movie.voteAverage)
        Divider(
            modifier = Modifier
                .padding(16.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            ReleaseDateSection(date = movie.releaseDate.toString())
            Spacer(modifier = Modifier.padding(end = 8.dp))
            GenreSection(genre = movie.genres)
        }
        Divider(
            modifier = Modifier
                .padding(16.dp)
        )
        DescriptionSection(movie.overview)
        Divider(
            modifier = Modifier
                .padding(16.dp)
        )
        CastSection(credit.cast)
        Spacer(modifier = Modifier.padding(bottom = 24.dp))

    }
}

@Composable
fun CastSection(cast: List<Cast>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = "Cast",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 4.dp),
            color = Color.White,
        )
        LazyRow() {
            items(
                items = cast,
                key = { cast -> cast.castId ?: 0 }
            ) { cast ->
                Column(
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    AsyncImage(
                        model = "$IMAGE_URL${cast.profilePath}",
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.outline_broken_image_24),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.outline_account_circle_24),
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Text(
                        text = cast.character.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .width(width = 120.dp)
                    )
                    Text(
                        text = cast.name.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier
                            .width(width = 120.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun DescriptionSection(overview: String?) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Overview",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 4.dp),
            color = Color.White,
        )
        Text(
            text = overview.toString(),
            textAlign = TextAlign.Justify,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreSection(
    genre: List<GenresItem?>?
) {
    Column {
        Text(
            text = "Genre",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 4.dp
            ),
            color = Color.White,
        )
        FlowRow(
            modifier = Modifier
                .wrapContentWidth()
        ) {
            genre?.forEach {
                Surface(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, color = Color.Transparent, RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = it?.name.toString(),
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReleaseDateSection(
    date: String,
) {
    Column {
        Text(
            text = "Release Date",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 12.dp
            ),
            color = Color.White,
        )
        Text(
            text = date,
            color = Color.White,
        )
    }
}

@Composable
fun HeaderTitle(
    title: String?,
    runtime: Int?,
    voteAverage: Double?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title.toString(),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = Color.White,
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.4f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeaderItem(
                icon = painterResource(id = R.drawable.ic_time)
            ) {
                "$runtime minutes"
            }
            HeaderItem(
                icon = painterResource(id = R.drawable.ic_star)
            ) {
                "$voteAverage".substring(0, 1) + "/10"
            }
        }
    }
}

@Composable
fun HeaderItem(
    icon: Painter,
    itemValue: () -> String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 6.dp)
        )
        Text(
            text = itemValue(),
            fontSize = 12.sp,
            color = Color.White,
        )
    }
}
