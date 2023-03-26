package com.pslpro.futuremusic.ui.view.musicPlayer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pslpro.futuremusic.UIViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicLyrics(uiViewModel: UIViewModel) {

    val scope = rememberCoroutineScope()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable (
                onClick = {
                    scope.launch { uiViewModel.musicPlayerPageState.scrollToPage(0) }
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ){
        item{
            Text(text = "歌词")
        }
    }
}