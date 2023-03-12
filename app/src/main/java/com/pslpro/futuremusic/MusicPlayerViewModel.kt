package com.pslpro.futuremusic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MusicPlayerViewModel: ViewModel() {

    var isPlaying by mutableStateOf(false)

    var isOpenPlayList by mutableStateOf(false)

    var sliderPosition by mutableStateOf(0f)

}