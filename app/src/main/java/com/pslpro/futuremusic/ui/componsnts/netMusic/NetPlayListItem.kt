package com.pslpro.futuremusic.ui.componsnts.netMusic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pslpro.futuremusic.model.PlaylistsBean
import com.pslpro.futuremusic.utils.FormatUtil
import com.pslpro.futuremusic.R

@Composable
fun NetPlayListItem(item: PlaylistsBean, onPlayListItemClick: ()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable {
                onPlayListItemClick()
            }
    ) {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .clip(MaterialTheme.shapes.medium)
        ){
            AsyncImage(
                item.coverImgUrl,
                contentDescription = "",
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .background(Color(0XFFFFFFFF).copy(alpha = 0.7f))
                    .clip(MaterialTheme.shapes.medium)

            ) {
                Image(painter = painterResource(id = R.drawable.sv_play), contentDescription = "播放量", Modifier.size(12.dp))
                Text(
                    text = FormatUtil.formatToTenThousand(item.playCount),
                    fontSize = 12.sp,
                    color = Color.Black,

                )
            }

        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(text = item.name, fontWeight = FontWeight.Bold)
//            Text(text = item.description)
        }

    }
}