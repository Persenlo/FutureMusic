package com.pslpro.futuremusic.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.utils.FormatUtil

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

        itemsIndexed(localMusic) { index, card ->
            LaunchedEffect(Unit) {
                musicPlayerViewModel.loadBitmapIfNeeded(context, index)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .height(72.dp)
                    .clickable { }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (card.cover != null) {
                        Image(bitmap = card.cover.asImageBitmap(), contentDescription = "", modifier = Modifier
                            .padding(start = 8.dp)
                            .width(64.dp)
                            .height(64.dp)
                            .clip(MaterialTheme.shapes.small)
                        )
                    } else {
                        // some placeholder
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                    ) {
                        Text(text = card.songTitle)
                        Text(text = card.artist)
                    }
                }
                
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Text(text = FormatUtil.formatMill(card.duration))
                }
            }
        }
        
    }
}