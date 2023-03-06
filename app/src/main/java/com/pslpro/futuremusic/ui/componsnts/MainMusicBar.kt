package com.pslpro.futuremusic.ui.componsnts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.R

@Composable
fun MainMusicBar(
    musicPlayerViewModel: MusicPlayerViewModel,
    onMusicBarClick: () -> Unit,
    onNextClick: () -> Unit,
    onListClick: () -> Unit,
    onPlayClick: () -> Unit
){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onMusicBarClick() }
    ) {

        Row(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "封面",
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                )
            Column (
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(start = 8.dp)
            ){
                Text(text = "标题")
                Text(text = "歌手名称")
            }
        }

        Row(
            modifier = Modifier.padding(end = 8.dp)
        ) {
            IconButton(onClick = { onPlayClick() }) {
                if (musicPlayerViewModel.isPlaying){
                    Icon(painter = painterResource(id = R.drawable.sv_pause), contentDescription = "暂停", Modifier.size(24.dp))
                }else{
                    Icon(painter = painterResource(id = R.drawable.sv_play), contentDescription = "播放", Modifier.size(24.dp))
                }
            }
            IconButton(onClick = { onNextClick() }) {
                Icon(painter = painterResource(id = R.drawable.sv_next), contentDescription = "下一首", Modifier.size(24.dp))
            }
            IconButton(onClick = { onListClick() }) {
                Icon(painter = painterResource(id = R.drawable.sv_playlist), contentDescription = "歌单", Modifier.size(24.dp))
            }
        }

    }

}