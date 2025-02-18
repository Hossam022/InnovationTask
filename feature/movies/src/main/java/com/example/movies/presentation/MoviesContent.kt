package com.example.movies.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.common.extensions.getYearFromDate
import com.example.common.utils.Constants
import com.example.common.utils.TestTag
import com.example.design.components.CircularProgress
import com.example.design.components.ErrorDialog
import com.example.design.components.LoadingPage
import com.example.design.theme.InnovationTaskTheme
import com.example.movies.domain.model.MovieUIModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesContent(
    trendingPagingItem: LazyPagingItems<MovieUIModel>, onNavigateDetailScreen: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }

    val listState = rememberLazyListState()

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        /**
         * workaround if condition, to save the scroll position when back from movie details.
         * alternatively - but it won't update listState firstVisibleItemIndex - use
         * trendingPagingItem.rememberLazyListState() [LazyPagingItems<T>.rememberLazyListState()] extension.
         * More info: https://issuetracker.google.com/issues/177245496.
         */
        /**
         * workaround if condition, to save the scroll position when back from movie details.
         * alternatively - but it won't update listState firstVisibleItemIndex - use
         * trendingPagingItem.rememberLazyListState() [LazyPagingItems<T>.rememberLazyListState()] extension.
         * More info: https://issuetracker.google.com/issues/177245496.
         */
        if (trendingPagingItem.itemCount != 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(TestTag.MoviesTrending),
                state = listState
            ) {

                stickyHeader {
                    Text(
                        text = "Trending Movies \uD83D\uDD25",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
                    )
                }

                items(count = trendingPagingItem.itemCount, key = {
                    "${trendingPagingItem[it]?.id}" + "${trendingPagingItem[it]?.title}"
                }) {
                    trendingPagingItem[it]?.let { movie ->
                        TrendingMovieItem(
                            modifier = Modifier.animateItemPlacement(),
                            movie = movie,
                            onNavigateDetailScreen = onNavigateDetailScreen
                        )
                    }
                }

            }
        }

        trendingPagingItem.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    LoadingPage(modifier = Modifier.fillMaxSize())
                }

                loadState.refresh is LoadState.Error -> {
                    val error = trendingPagingItem.loadState.refresh as LoadState.Error
                    AnimatedVisibility(visible = showDialog) {
                        ErrorDialog(errorMessage = error.error.localizedMessage!!,
                            onRetryClick = { retry() },
                            onDismiss = { showDialog = false })
                    }

                }

                loadState.append is LoadState.Loading -> {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgress(true)
                    }

                }
            }
        }

        AnimatedVisibility(visible = showButton, modifier = Modifier.align(Alignment.BottomEnd)) {
            ScrollToTopButton {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }

}

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.background
            )
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .padding(10.dp)


    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            "Scroll to Top Button",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.rotate(90f)
        )
    }
}

@Composable
fun TrendingMovieItem(
    movie: MovieUIModel, modifier: Modifier = Modifier, onNavigateDetailScreen: (String) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .clickable {
            onNavigateDetailScreen(movie.id.toString())
        }
        .testTag(TestTag.MoviesClickRow)) {
        MovieImage(movie)
        Spacer(modifier = Modifier.size(10.dp))

        Column {
            Text(
                text = movie.title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )
            Text(
                text = movie.year.getYearFromDate() ?: "",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
        }
    }

}

@Composable
fun MovieImage(movie: MovieUIModel) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(150.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (LocalInspectionMode.current) {
                Image(
                    imageVector = Icons.Default.Favorite,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Trending Movie Image",
                    contentScale = ContentScale.Crop
                )
            } else {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.IMAGE_BASE_URL}/w300/${movie.image}")
                        .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
                        .crossfade(true)
                        .build()
                )
                if (painter.state is AsyncImagePainter.State.Loading) {
                    CircularProgress(true)
                } else {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painter,
                        contentDescription = "Trending Movie Image",
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    }

}

@Preview
@Composable
private fun TrendingMovieItemPrev() {
    val movieUIModel = MovieUIModel(1, "Movie", "", "2024")
    InnovationTaskTheme {
        TrendingMovieItem(movieUIModel) {

        }
    }
}
