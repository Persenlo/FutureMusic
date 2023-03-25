package com.pslpro.futuremusic

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.pslpro.futuremusic.nav.MainNav
import com.pslpro.futuremusic.service.MusicPlayerManager
import com.pslpro.futuremusic.ui.theme.FutureMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化音乐播放器
        MusicPlayerManager.init(application = application)
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
    
    val navController = rememberNavController()


    FutureMusicTheme {

        MainNav(
            navController = navController,
            context = context,
            activity = activity,
            musicPlayerViewModel = musicPlayerViewModel,
            uiViewModel = uiViewModel)
    }
}