package com.pslpro.futuremusic.ui.view.musicPlayer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.service.MusicPlayerManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicCover(uiViewModel: UIViewModel) {

    val currentPlay by MusicPlayerManager.getCurrentPlay().observeAsState()
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable (
                onClick = {
                    scope.launch { uiViewModel.musicPlayerPageState.scrollToPage(1) }
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
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