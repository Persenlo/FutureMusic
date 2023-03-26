package com.pslpro.futuremusic.model

data class NetPlayListModel(

    val playlists: List<PlaylistsBean>


)

data class PlaylistsBean(
    var name: String,
    var id: Long,
    var coverImgUrl: String,
    var playCount: Int,
    var description: String,
)
