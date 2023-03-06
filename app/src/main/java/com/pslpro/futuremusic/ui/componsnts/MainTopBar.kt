package com.pslpro.futuremusic.ui.componsnts


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainTopBar(){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Outlined.Menu, contentDescription = "菜单", tint = MaterialTheme.colorScheme.onPrimary)
        }

        Row() {
            Text(text = "本地音乐", color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.padding(start = 32.dp))
            Text(text = "网络音乐", color = MaterialTheme.colorScheme.onPrimary)
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "搜索", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }

}

