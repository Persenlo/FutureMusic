package com.pslpro.futuremusic.ui.navPage

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.nav.MainNavConfig
import com.pslpro.futuremusic.service.MusicPlayerManager
import com.pslpro.futuremusic.ui.componsnts.MusicControlBar
import com.pslpro.futuremusic.ui.componsnts.MusicTopBar
import com.pslpro.futuremusic.ui.view.musicPlayer.MusicCover
import com.pslpro.futuremusic.ui.view.musicPlayer.MusicLyrics

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MusicPlayerPage(
    navController: NavHostController,
    context: Context,
    activity: Activity?,
    musicPlayerViewModel: MusicPlayerViewModel,
    uiViewModel: UIViewModel
) {

    val currentPlay by MusicPlayerManager.getCurrentPlay().observeAsState()

    Scaffold(
        topBar = { MusicTopBar(
            onCloseClick = {
                navController.navigate(MainNavConfig.MAIN_PAGE){
                    popUpTo(MainNavConfig.MAIN_PAGE){
                        inclusive = true
                    }
                } }
        )
        },
        bottomBar = {
            MusicControlBar(
                musicPlayerViewModel,
                onPlayClick = {
                    musicPlayerViewModel.isPlaying = !musicPlayerViewModel.isPlaying
                }
            )
        },
    ) {
        HorizontalPager(
            pageCount = 2,
            state = uiViewModel.musicPlayerPageState,
            contentPadding = it,
            modifier = Modifier.fillMaxSize()
        ){
            when(it){
                //0为歌曲封面
                0 -> {
                    MusicCover(uiViewModel = uiViewModel)
                }
                //1为歌词界面
                1 -> {
                    MusicLyrics(uiViewModel = uiViewModel)
                }
            }
        }
    }
}