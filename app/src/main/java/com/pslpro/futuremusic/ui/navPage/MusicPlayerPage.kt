package com.pslpro.futuremusic.ui.navPage

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.nav.MainNavConfig
import com.pslpro.futuremusic.ui.componsnts.MusicControlBar
import com.pslpro.futuremusic.ui.componsnts.MusicTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerPage(
    navController: NavHostController,
    context: Context,
    activity: Activity?,
    musicPlayerViewModel: MusicPlayerViewModel,
    uiViewModel: UIViewModel
) {
    Scaffold(
        topBar = { MusicTopBar(
            onCloseClick = {
                navController.navigate(MainNavConfig.MAIN_PAGE){
                    popUpTo(MainNavConfig.MAIN_PAGE){
                        inclusive = true
                    }
                } }
        )
        },
        bottomBar = {
            MusicControlBar(
                musicPlayerViewModel,
                onPlayClick = {
                    musicPlayerViewModel.isPlaying = !musicPlayerViewModel.isPlaying
                }
            )
        },
    ) {
        Text(text = "", Modifier.padding(it))
    }
}