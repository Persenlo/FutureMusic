package com.pslpro.futuremusic.service


import android.media.MediaPlayer
import androidx.lifecycle.LifecycleService


class MusicPlayerService: LifecycleService() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        init()
    }

    private fun init() {
        TODO("Not yet implemented")
    }

}