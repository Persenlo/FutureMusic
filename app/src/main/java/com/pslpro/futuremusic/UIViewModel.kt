package com.pslpro.futuremusic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UIViewModel: ViewModel() {

    @OptIn(ExperimentalFoundationApi::class)
    var mainPageState by mutableStateOf(PagerState())

    @OptIn(ExperimentalFoundationApi::class)
    var musicPlayerPageState by mutableStateOf(PagerState())

}



