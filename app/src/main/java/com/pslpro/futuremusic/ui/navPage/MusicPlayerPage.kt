package com.pslpro.futuremusic.ui.navPage

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.nav.MainNavConfig
import com.pslpro.futuremusic.service.MusicPlayerManager
import com.pslpro.futuremusic.ui.componsnts.MusicControlBar
import com.pslpro.futuremusic.ui.componsnts.MusicTopBar

@OptIn(ExperimentalMaterial3Api::class)
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (currentPlay != null){
                Image(
                    bitmap = currentPlay!!.cover!!.asImageBitmap(),
                    contentDescription = "专辑图片",
                    modifier = Modifier
                        .width(256.dp)
                        .height(256.dp)
                )
            }
        }
    }
}