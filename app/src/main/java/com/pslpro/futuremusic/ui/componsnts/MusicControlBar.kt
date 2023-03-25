package com.pslpro.futuremusic.ui.componsnts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.google.android.exoplayer2.Player
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.R
import com.pslpro.futuremusic.service.MusicPlayerManager
import com.pslpro.futuremusic.utils.FormatUtil

@Composable
fun MusicControlBar(
    musicPlayerViewModel: MusicPlayerViewModel,
    onPlayClick: ()->Unit
) {

    val currentState by MusicPlayerManager.getPlayState().observeAsState()
    val currentProcess by MusicPlayerManager.getProcess().observeAsState()
    val currentDuration by MusicPlayerManager.getDuration().observeAsState()
    val currentPlayMode by MusicPlayerManager.getPlayMode().observeAsState()

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
            //进度条
            if (currentDuration!=null&&currentProcess!=null){
                Text(text = FormatUtil.formatMill(currentProcess!!.toLong()), fontSize = 12.sp)
                Spacer(modifier = Modifier.padding(start = 16.dp))

                Slider(
                    thumb = {},
                    modifier = Modifier.width(sliderWidth),
                    value = currentProcess!!.toFloat()/currentDuration!!.toFloat(),
                    onValueChange = {
                        MusicPlayerManager.seekTo((it * currentDuration!!).toInt())
                    }
                )

                Spacer(modifier = Modifier.padding(start = 16.dp))
                Text(text = FormatUtil.formatMill(currentDuration!!.toLong()), fontSize = 12.sp)
            }else{
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
                //播放模式
                IconButton(onClick = { /*TODO*/ }) {
                    if (currentPlayMode!=null){
                        when(currentPlayMode){
                            //随机播放
                            100 -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.sv_random),
                                    contentDescription = "播放模式",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            //切换到顺序播放
                                            MusicPlayerManager.stopShufflePlay()
                                            MusicPlayerManager.repeatModeOff()
                                        }
                                )
                            }
                            //顺序播放
                            Player.REPEAT_MODE_OFF -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.sv_cycle_no),
                                    contentDescription = "播放模式",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            //切换到列表循环
                                            MusicPlayerManager.repeatModeOn()
                                        }
                                )
                            }
                            //列表循环
                            Player.REPEAT_MODE_ALL -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.sv_cycle),
                                    contentDescription = "播放模式",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            //切换到单曲循环
                                            MusicPlayerManager.repeatModeOne()
                                        }
                                )
                            }
                            //单曲循环
                            Player.REPEAT_MODE_ONE -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.sv_cycle_one),
                                    contentDescription = "播放模式",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            //切换到随机播放
                                            MusicPlayerManager.shufflePlay()
                                        }
                                )
                            }
                            //随机播放

                        }
                    }else{
                        Icon(
                            painter = painterResource(id = R.drawable.sv_cycle),
                            contentDescription = "播放模式",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Row() {
                    IconButton(onClick = {
                        MusicPlayerManager.skipToPrevious()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.sv_before),
                            contentDescription = "上一首"
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    //播放切换
                    IconButton(onClick = {
                        if (currentState!!){
                            MusicPlayerManager.pause()
                        }else{
                            MusicPlayerManager.play()
                        }
                    }) {
                        if (currentState!=null){
                            if (currentState!!){
                                Icon(painter = painterResource(id = R.drawable.sv_pause), contentDescription = "点击暂停")
                            }else{
                                Icon(painter = painterResource(id = R.drawable.sv_play), contentDescription = "点击播放")
                            }
                        }else{
                            Icon(painter = painterResource(id = R.drawable.sv_pause), contentDescription = "暂停")
                        }
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    //下一首
                    IconButton(onClick = {
                        MusicPlayerManager.skipToNext()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.sv_next),
                            contentDescription = "下一首"
                        )
                    }
                }
                //播放列表
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