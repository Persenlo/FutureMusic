package com.pslpro.futuremusic.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainNetMusic(
    
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        item { 
            Text(text = "网络音乐")
        }
    }
}