package com.pslpro.futuremusic.ui.componsnts

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pslpro.futuremusic.service.MusicPlayerManager

@Composable
fun MusicTopBar(
    onCloseClick: () -> Unit
){

    val currentPlay by MusicPlayerManager.getCurrentPlay().observeAsState()

    ConstraintLayout(

        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {

        val (leftButtonRef,titleRef,menuButtonRef) = remember { createRefs() }

        IconButton(
            onClick = { onCloseClick() },
            modifier = Modifier.constrainAs(leftButtonRef){
                top.linkTo(parent.top,16.dp)
                bottom.linkTo(parent.bottom,16.dp)
                start.linkTo(parent.start,0.dp)
            }
        ) {
            Icon(imageVector = Icons.Outlined.Close, contentDescription = "关闭")
        }
        Column(
            modifier = Modifier.constrainAs(titleRef){
                top.linkTo(parent.top,16.dp)
                bottom.linkTo(parent.bottom,16.dp)
                start.linkTo(parent.start,56.dp)
                end.linkTo(parent.end,56.dp)
            }
        ) {
            if (currentPlay != null){
                Text(text = currentPlay!!.songTitle, textAlign = TextAlign.Center, fontSize = 18.sp , modifier = Modifier.fillMaxWidth() )
                Text(text = currentPlay!!.artist, textAlign = TextAlign.Center, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
            }else{
                Text(text = "歌曲名称", textAlign = TextAlign.Center, fontSize = 18.sp , modifier = Modifier.fillMaxWidth() )
                Text(text = "歌手", textAlign = TextAlign.Center, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
            }
        }
    }

}