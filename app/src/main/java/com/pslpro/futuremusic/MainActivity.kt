package com.pslpro.futuremusic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pslpro.futuremusic.ui.componsnts.MainMusicBar
import com.pslpro.futuremusic.ui.componsnts.MainPlayList
import com.pslpro.futuremusic.ui.componsnts.MainTopBar
import com.pslpro.futuremusic.ui.theme.FutureMusicTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutureMusicMain()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FutureMusicMain() {

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val musicPlayerViewModel: MusicPlayerViewModel = viewModel()
    val uiViewModel:UIViewModel = viewModel()


    FutureMusicTheme {

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
                            val intent = Intent(context,MusicActivity::class.java)
                            activity?.startActivity(intent)
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
                                Text(text = "主页")
                            }
                            //1为网络音乐
                            1 -> {
                                Text(text = "网络音乐")
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
}