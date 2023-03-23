package com.pslpro.futuremusic.nav

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pslpro.futuremusic.MusicPlayerViewModel
import com.pslpro.futuremusic.UIViewModel
import com.pslpro.futuremusic.ui.navPage.MainPage
import com.pslpro.futuremusic.ui.navPage.MusicPlayerPage

@Composable
fun MainNav(
    navController: NavHostController,
    context: Context,
    activity: Activity?,
    musicPlayerViewModel: MusicPlayerViewModel,
    uiViewModel: UIViewModel
){

    NavHost(navController = navController, startDestination = MainNavConfig.MAIN_PAGE){
        composable("mainPage"){
            MainPage(navController,context,activity,musicPlayerViewModel,uiViewModel)
        }
        composable("musicPlayerPage"){
            MusicPlayerPage(navController,context,activity,musicPlayerViewModel,uiViewModel)
        }
    }

}