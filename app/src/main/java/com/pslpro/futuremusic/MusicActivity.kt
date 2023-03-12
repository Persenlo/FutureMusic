package com.pslpro.futuremusic

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pslpro.futuremusic.ui.componsnts.MusicControlBar
import com.pslpro.futuremusic.ui.componsnts.MusicTopBar
import com.pslpro.futuremusic.ui.theme.FutureMusicTheme

class MusicActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicView()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicView() {

    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    val musicPlayerViewModel: MusicPlayerViewModel = viewModel()

    FutureMusicTheme {
        Scaffold(
            topBar = { MusicTopBar(
                onCloseClick = { activity?.finish() }
            )},
            bottomBar = {
                MusicControlBar(musicPlayerViewModel)
            },
        ) {
            Text(text = "", Modifier.padding(it))
        }
    }
}