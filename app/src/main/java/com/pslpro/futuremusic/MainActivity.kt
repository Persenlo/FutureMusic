package com.pslpro.futuremusic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pslpro.futuremusic.ui.componsnts.MainMusicBar
import com.pslpro.futuremusic.ui.componsnts.MainPlayList
import com.pslpro.futuremusic.ui.componsnts.MainTopBar
import com.pslpro.futuremusic.ui.theme.FutureMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutureMusicMain()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FutureMusicMain() {

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val musicPlayerViewModel: MusicPlayerViewModel = viewModel()

    FutureMusicTheme {

        Scaffold(
            topBar = { MainTopBar() },
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
            Text(text = "",Modifier.padding(it))

        }
        MainPlayList(musicPlayerViewModel = musicPlayerViewModel)

    }
}