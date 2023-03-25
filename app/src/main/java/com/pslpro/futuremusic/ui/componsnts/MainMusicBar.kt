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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.R
import com.pslpro.futuremusic.service.MusicPlayerManager

@Composable
fun MainMusicBar(
    musicPlayerViewModel: MusicPlayerViewModel,
    onMusicBarClick: () -> Unit,
    onNextClick: () -> Unit,
    onListClick: () -> Unit,
    onPlayClick: () -> Unit
){

    val currentPlay by MusicPlayerManager.getCurrentPlay().observeAsState()
    val currentState by MusicPlayerManager.getPlayState().observeAsState()

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
            if (currentPlay!=null){
                Image(
                    bitmap = currentPlay!!.cover!!.asImageBitmap(),
                    contentDescription = "封面",
                    modifier = Modifier
                        .width(56.dp)
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "封面",
                    modifier = Modifier
                        .width(56.dp)
                        .height(56.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
            Column (
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(56.dp)
                    .width(180.dp)
            ){
                if (currentPlay != null){
                    Text(text = currentPlay!!.songTitle, fontSize = 18.sp)
                    Text(text = currentPlay!!.artist, fontSize = 14.sp)
                }else{
                    Text(text = "标题", fontSize = 18.sp)
                    Text(text = "歌手名称", fontSize = 14.sp)
                }
            }
        }

        Row(
            modifier = Modifier.padding(end = 8.dp)
        ) {
            IconButton(onClick = { onPlayClick() }) {
                if (currentState!=null){
                    if (currentState!!){
                        Icon(painter = painterResource(id = R.drawable.sv_pause), contentDescription = "点击暂停", Modifier.size(24.dp))
                    }else{
                        Icon(painter = painterResource(id = R.drawable.sv_play), contentDescription = "点击播放", Modifier.size(24.dp))
                    }
                }else{
                    Icon(painter = painterResource(id = R.drawable.sv_pause), contentDescription = "暂停", Modifier.size(24.dp))
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