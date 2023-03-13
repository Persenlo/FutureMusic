package com.pslpro.futuremusic.ui.componsnts


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pslpro.futuremusic.UIViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainTopBar(
    onMenuClick: () -> Unit,
    onLocalClick: () -> Unit,
    onNetClick: () -> Unit,
    onSearchClick: () -> Unit,
    uiViewModel: UIViewModel
){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        IconButton(onClick = { onMenuClick() }) {
            Icon(imageVector = Icons.Outlined.Menu, contentDescription = "菜单", tint = MaterialTheme.colorScheme.onPrimary)
        }

        Row() {
            Button(onClick = { onLocalClick() }) {
                //当page页面为0时高亮
                if (uiViewModel.mainPageState.currentPage == 0){
                    Text(text = "本地音乐", color = MaterialTheme.colorScheme.onPrimary)
                }else{
                    Text(text = "本地音乐", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
            Spacer(modifier = Modifier.padding(start = 32.dp))
            Button(onClick = { onNetClick() }) {
                //当page页面为1时高亮
                if (uiViewModel.mainPageState.currentPage == 1){
                    Text(text = "网络音乐", color = MaterialTheme.colorScheme.onPrimary)
                }else{
                    Text(text = "网络音乐", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }

        IconButton(onClick = { onSearchClick() }) {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "搜索", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }

}

