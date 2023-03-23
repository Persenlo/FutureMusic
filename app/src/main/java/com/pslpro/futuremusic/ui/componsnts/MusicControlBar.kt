package com.pslpro.futuremusic.ui.componsnts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.R

@Composable
fun MusicControlBar(
    musicPlayerViewModel: MusicPlayerViewModel,
    onPlayClick: ()->Unit
) {

    //获取屏幕宽度
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    //计算合适的进度条宽度
    val sliderWidth =  screenWidthDp/3*2

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "0:00", fontSize = 12.sp)
            Spacer(modifier = Modifier.padding(start = 16.dp))


            Slider(
                thumb = {},
                modifier = Modifier.width(sliderWidth),
                value = musicPlayerViewModel.sliderPosition,
                onValueChange = { musicPlayerViewModel.sliderPosition = it }
            )


            Spacer(modifier = Modifier.padding(start = 16.dp))
            Text(text = "0:00", fontSize = 12.sp)
        }


        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val (processBarRef, controlButtonsRef) = remember { createRefs() }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(controlButtonsRef) {
                        top.linkTo(parent.top, 32.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sv_cycle),
                        contentDescription = "播放模式",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Row() {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.sv_before),
                            contentDescription = "上一首"
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    IconButton(onClick = { onPlayClick() }) {
                        if (musicPlayerViewModel.isPlaying){
                            Icon(painter = painterResource(id = R.drawable.sv_pause), contentDescription = "暂停")
                        }else{
                            Icon(painter = painterResource(id = R.drawable.sv_play), contentDescription = "播放")
                        }
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.sv_next),
                            contentDescription = "下一首"
                        )
                    }
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sv_playlist),
                        contentDescription = "播放列表",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}