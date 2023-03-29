package com.pslpro.futuremusic.ui.navPage.netMusic

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.NetMusicViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.model.PlaylistsBean
import com.pslpro.futuremusic.model.SongsBean
import com.pslpro.futuremusic.service.MusicPlayerManager
import com.pslpro.futuremusic.ui.componsnts.MainMusicBar

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayListSongs(
    navController: NavHostController,
    context: Context,
    activity: Activity?,
    musicPlayerViewModel: MusicPlayerViewModel,
    uiViewModel: UIViewModel,
    netMusicViewModel: NetMusicViewModel
) {


    val currentState by MusicPlayerManager.getPlayState().observeAsState()


    Scaffold(
        bottomBar = {
            MainMusicBar(
                musicPlayerViewModel = musicPlayerViewModel,
                onMusicBarClick = {
                    navController.navigate("musicPlayerPage")
                },
                onPlayClick = {
                    if (currentState!!){
                        MusicPlayerManager.pause()
                    }else{
                        MusicPlayerManager.play()
                    }
                },
                onNextClick = {
                    MusicPlayerManager.skipToNext()
                },
                onListClick = {
                    musicPlayerViewModel.isOpenPlayList = true
                })
        }
    ) {
        Column(Modifier.padding(it)) {
            val playListSongs = netMusicViewModel.playListSongs.collectAsLazyPagingItems()

            // 首次加载业务逻辑
            when (playListSongs.loadState.refresh) {
                is LoadState.NotLoading -> {
                    ContentInfoList(
                        navController = navController,
                        collectAsLazyPagingIDataList = playListSongs,
                        context = context,
                        netMusicViewModel = netMusicViewModel
                    )
                }
                is LoadState.Error -> com.pslpro.futuremusic.ui.view.ErrorPage() { playListSongs.refresh() }
                is LoadState.Loading -> com.pslpro.futuremusic.ui.view.LoadingPageUI()
            }
        }
    }
    



}


@SuppressLint("UnrememberedMutableState")
@ExperimentalCoilApi
@Composable
fun ContentInfoList(
    context: Context,
    collectAsLazyPagingIDataList: LazyPagingItems<SongsBean>,
    netMusicViewModel: NetMusicViewModel,
    navController: NavHostController
) {
    val lazyListState = rememberLazyListState()
    val focusIndex by derivedStateOf { lazyListState.firstVisibleItemIndex }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ){
            AsyncImage(
                model = netMusicViewModel.choosePlayList.value!!.coverImgUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.TopStart)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "关闭",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
                Text(
                    text = netMusicViewModel.choosePlayList.value!!.name,
                    color = MaterialTheme.colorScheme.background,
                    textAlign = TextAlign.Start
                )
            }

        }

        Spacer(modifier = Modifier.padding(top = 8.dp))

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .nestedScroll(object : NestedScrollConnection{
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        return super.onPreScroll(available, source)
                    }
                })
        ) {

            itemsIndexed(collectAsLazyPagingIDataList) { index, item ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .width(56.dp)
                            .height(56.dp)
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        AsyncImage(model = item!!.al.picUrl, contentDescription = "")
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 12.dp)
                    ) {
                        Text(text = item!!.name, color = MaterialTheme.colorScheme.onBackground)
                        Row() {
                            for (arBean in item.ar) {
                                Text(text = arBean.name, color = MaterialTheme.colorScheme.onBackground)
                            }
                        }
                    }
                }

            }

            // 加载下一页业务逻辑
            when (collectAsLazyPagingIDataList.loadState.append) {
                is LoadState.NotLoading -> {
                    itemsIndexed(collectAsLazyPagingIDataList) { index, item ->

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