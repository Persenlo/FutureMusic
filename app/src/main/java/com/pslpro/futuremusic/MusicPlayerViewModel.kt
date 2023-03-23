package com.pslpro.futuremusic

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pslpro.futuremusic.model.LocalMusicModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MusicPlayerViewModel: ViewModel() {

    var isPlaying by mutableStateOf(false)

    var isOpenPlayList by mutableStateOf(false)

    var sliderPosition by mutableStateOf(0f)

    val localMusic = mutableStateListOf<LocalMusicModel>()

    private var initialized = false
    private val backgroundScope = viewModelScope.plus(Dispatchers.Default)

    fun initializeListIfNeeded(context: Context) {
        if (initialized) return
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST
        )
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val duration = cursor.getInt(durationColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

//                val durationString = duration
                localMusic.add(LocalMusicModel(contentUri, id, null, title, artist, duration.toLong()))
            }
        }
        initialized = true
    }

    fun loadBitmapIfNeeded(context: Context, index: Int) {
        if (localMusic[index].cover != null) return
        // if this is gonna lag during scrolling, you can move it on a background thread
        backgroundScope.launch {
            val bitmap = getAlbumArt(context, localMusic[index].contentUri)
            localMusic[index] = localMusic[index].copy(cover = bitmap)
        }
    }

    private fun getAlbumArt(context: Context, uri: Uri): Bitmap {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, uri)
        val data = mmr.embeddedPicture
        return if(data != null){
            BitmapFactory.decodeByteArray(data, 0, data.size)
        }else{
            BitmapFactory.decodeResource(context.resources, R.drawable.default_cover)
        }
    }

}