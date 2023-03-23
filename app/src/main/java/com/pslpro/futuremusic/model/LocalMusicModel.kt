package com.pslpro.futuremusic.model

import android.graphics.Bitmap
import android.net.Uri

data class LocalMusicModel(
    val contentUri: Uri,
    val songId: Long,
    val cover: Bitmap?,
    val songTitle: String,
    val artist: String,
    val duration: Long
)
