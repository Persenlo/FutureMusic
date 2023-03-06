package com.pslpro.futuremusic.ui.componsnts

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pslpro.futuremusic.MusicPlayerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPlayList(
    musicPlayerViewModel: MusicPlayerViewModel
){

    if (musicPlayerViewModel.isOpenPlayList){
        ModalBottomSheet(
            onDismissRequest = { musicPlayerViewModel.isOpenPlayList = false },
            modifier = Modifier.height(372.dp)
        ) {
            Text(text = "播放列表")
        }
    }

}