package com.pslpro.futuremusic.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.pslpro.futuremusic.NetMusicViewModel
import com.pslpro.futuremusic.model.PlaylistsBean
import com.pslpro.futuremusic.ui.componsnts.netMusic.NetPlayListItem

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainNetMusic(
    
){

    val netMusicViewModel: NetMusicViewModel = viewModel()
    val playlist = netMusicViewModel.playList.collectAsLazyPagingItems()

    val context: Context = LocalContext.current

    // 首次加载业务逻辑
    when (playlist.loadState.refresh) {
        is LoadState.NotLoading -> {
            ContentInfoList(
                collectAsLazyPagingIDataList = playlist,
                context = context,
                viewModel = netMusicViewModel
            )
        }
        is LoadState.Error -> ErrorPage() { playlist.refresh() }
        is LoadState.Loading -> LoadingPageUI()
    }
}


@SuppressLint("UnrememberedMutableState")
@ExperimentalCoilApi
@Composable
fun ContentInfoList(
    context: Context,
    collectAsLazyPagingIDataList: LazyPagingItems<PlaylistsBean>,
    viewModel: NetMusicViewModel
) {
    val lazyListState = rememberLazyListState()
    val focusIndex by derivedStateOf { lazyListState.firstVisibleItemIndex }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(collectAsLazyPagingIDataList) { index, item ->

            NetPlayListItem(
                item = item!!,
                onPlayListItemClick = {

                }
            )

        }

        // 加载下一页业务逻辑
        when (collectAsLazyPagingIDataList.loadState.append) {
            is LoadState.NotLoading -> {
                itemsIndexed(collectAsLazyPagingIDataList) { index, item ->
                    NetPlayListItem(
                        item = item!!,
                        onPlayListItemClick = {

                        }
                    )
                }
            }
            is LoadState.Error -> item {
                NextPageLoadError {
                    collectAsLazyPagingIDataList.retry()
                }
            }
            LoadState.Loading -> item {
                LoadingPageUI()
            }
        }
    }
}


/**
 * 页面加载失败重试
 * */
@Composable
fun ErrorPage(onclick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = onclick,
        ) {
            Text(text = "网络不佳，请点击重试")
        }
    }
}

/**
 * 加载中动效
 * */
@Composable
fun LoadingPageUI() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(170.dp), contentAlignment = Alignment.Center
    ) {
        val animator by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(800, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(80f, 80f) {
                drawArc(
                    color = Color(0XFF18ffff),
                    startAngle = 0f,
                    sweepAngle = animator,
                    useCenter = false,
                    size = Size(80 * 2f, 80 * 2f),
                    style = Stroke(12f),
                    alpha = 0.6f,
                )
            }
        }
    }
}

/**
 * 加载下一页失败
 * */
@Composable
fun NextPageLoadError(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(onClick = onClick) {
            Text(text = "重试")
        }
    }
}