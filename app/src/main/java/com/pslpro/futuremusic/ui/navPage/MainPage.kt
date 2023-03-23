package com.pslpro.futuremusic.ui.navPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pslpro.futuremusic.MusicActivity
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.ui.componsnts.MainMusicBar
import com.pslpro.futuremusic.ui.componsnts.MainPlayList
import com.pslpro.futuremusic.ui.componsnts.MainTopBar
import com.pslpro.futuremusic.ui.componsnts.PermissionGetDialog
import com.pslpro.futuremusic.ui.view.MainLocalMusic
import com.pslpro.futuremusic.ui.view.MainNetMusic
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(
    navController: NavHostController,
    context: Context,
    activity: Activity?,
    musicPlayerViewModel: MusicPlayerViewModel,
    uiViewModel: UIViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //导航内容
    data class NavItems(val icon: ImageVector, val name: String)
    val items = listOf(
        NavItems(Icons.Default.AccountCircle,"我的账号"),
        NavItems(Icons.Default.Settings, "设置"),
        NavItems(Icons.Default.ExitToApp, "退出")
    )

    val selectedItem = remember { mutableStateOf(items[0]) }

    //权限获取
    PermissionGetDialog()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,

        //主界面内容
        content = {
            Scaffold(
                topBar = { MainTopBar(
                    uiViewModel = uiViewModel,
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    },
                    onLocalClick = {
                        scope.launch { uiViewModel.mainPageState.scrollToPage(0) }
                    },
                    onNetClick = {
                        scope.launch { uiViewModel.mainPageState.scrollToPage(1) }
                    },
                    onSearchClick = {

                    }
                ) },
                bottomBar = { MainMusicBar(
                    musicPlayerViewModel = musicPlayerViewModel,
                    onMusicBarClick = {
                        navController.navigate("musicPlayerPage")
                    },
                    onPlayClick = {
                        musicPlayerViewModel.isPlaying = !musicPlayerViewModel.isPlaying
                    },
                    onNextClick = {
                        Toast.makeText(context,"下一首",Toast.LENGTH_SHORT).show()
                    },
                    onListClick = {
                        musicPlayerViewModel.isOpenPlayList = true
                    }
                ) }
            ) {
                //主页切换使用Pager
                HorizontalPager(
                    pageCount = 2,
                    state = uiViewModel.mainPageState,
                    contentPadding = it,
                    modifier = Modifier.fillMaxSize()

                ) {
                    when(it){
                        //0为本地音乐
                        0 -> {
                            MainLocalMusic(musicPlayerViewModel= musicPlayerViewModel)
                        }
                        //1为网络音乐
                        1 -> {
                            MainNetMusic()
                        }
                    }
                }

            }
            MainPlayList(musicPlayerViewModel = musicPlayerViewModel)
        },



        //抽屉内容
        drawerContent = {
            ModalDrawerSheet(Modifier.width(IntrinsicSize.Max)) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .fillMaxWidth()
                        .height(128.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {

                }
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    )
}