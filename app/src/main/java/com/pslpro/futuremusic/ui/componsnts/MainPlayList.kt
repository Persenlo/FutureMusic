package com.pslpro.futuremusic.ui.componsnts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.R
import com.pslpro.futuremusic.service.MusicPlayerManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPlayList(
    musicPlayerViewModel: MusicPlayerViewModel
){

    val playlist by MusicPlayerManager.getPlayList().observeAsState(listOf())
    val currentPlay by MusicPlayerManager.getCurrentPlay().observeAsState()

    if (musicPlayerViewModel.isOpenPlayList){
        ModalBottomSheet(
            onDismissRequest = { musicPlayerViewModel.isOpenPlayList = false },
            modifier = Modifier.height(372.dp)
        ) {

            //播放列表为空时，显示提示文字
            if (playlist.isEmpty()){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "播放列表为空", modifier = Modifier.padding(bottom = 56.dp))
                }
            }

            //播放列表不为空时，显示播放列表
            LazyColumn{
                items(playlist.size){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                MusicPlayerManager.play(playlist,it)
                            }
                            .padding(vertical = 0.dp, horizontal = 24.dp)
                    ) {
                        Text(text = playlist.get(it).songTitle, fontSize = 20.sp)
                        Row(verticalAlignment = Alignment.CenterVertically,) {
                            val item = playlist.get(it)
                            if (currentPlay?.songTitle == item.songTitle){
                                Icon(
                                    painter = painterResource(id = R.drawable.sv_play),
                                    contentDescription = "当前播放",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "移除当前歌曲")
                            }
                        }

                    }
                }
            }
        }
    }

}