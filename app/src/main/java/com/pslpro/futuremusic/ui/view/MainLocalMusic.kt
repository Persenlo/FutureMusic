package com.pslpro.futuremusic.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import com.pslpro.futuremusic.MusicPlayerViewModel

@Composable
fun MainLocalMusic(musicPlayerViewModel: MusicPlayerViewModel) {

    val context = LocalContext.current
    val localMusic = musicPlayerViewModel.localMusic
    LaunchedEffect(Unit) {
        musicPlayerViewModel.initializeListIfNeeded(context)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        item {
            Text(text = "本地音乐")
        }
        itemsIndexed(localMusic) { index, card ->
            LaunchedEffect(Unit) {
                musicPlayerViewModel.loadBitmapIfNeeded(context, index)
            }
            if (card.cover != null) {
                Image(bitmap = card.cover.asImageBitmap(), "...")
            } else {
                // some placeholder
            }
        }
    }
}