package com.pslpro.futuremusic.nav

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.NetMusicViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.ui.navPage.MainPage
import com.pslpro.futuremusic.ui.navPage.MusicPlayerPage
import com.pslpro.futuremusic.ui.navPage.netMusic.PlayListSongs

@Composable
fun MainNav(
    navController: NavHostController,
    context: Context,
    activity: Activity?,
    musicPlayerViewModel: MusicPlayerViewModel,
    uiViewModel: UIViewModel,
    netMusicViewModel: NetMusicViewModel
){

    NavHost(navController = navController, startDestination = MainNavConfig.MAIN_PAGE){
        composable(MainNavConfig.MAIN_PAGE){
            MainPage(navController,context,activity,musicPlayerViewModel,uiViewModel,netMusicViewModel)
        }
        composable(MainNavConfig.MUSIC_PLAYER_PAGE){
            MusicPlayerPage(navController,context,activity,musicPlayerViewModel,uiViewModel,netMusicViewModel)
        }
        composable(MainNavConfig.NET_PLAYLIST_DETAIL_PAGE){
            PlayListSongs(navController,context,activity,musicPlayerViewModel,uiViewModel,netMusicViewModel)
        }
    }

}