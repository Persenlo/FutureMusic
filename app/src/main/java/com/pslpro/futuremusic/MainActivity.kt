package com.pslpro.futuremusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pslpro.futuremusic.ui.componsnts.MainMusicBar
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
    FutureMusicTheme {
        Scaffold(
            topBar = { MainTopBar() },
            bottomBar = { MainMusicBar() }
        ) {
            Text(text = "",Modifier.padding(it))
        }

    }
}