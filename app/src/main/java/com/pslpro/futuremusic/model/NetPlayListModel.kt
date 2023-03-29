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



/**
 * 歌单歌曲详情
 * ar:艺术家详情
 * al:封面
 */
data class PlaylistSongsModel(
    var songs: List<SongsBean>
)
data class SongsBean(
    var name: String,
    var id: Long,
    var ar: List<ArBean>,
    var al: AlBean,
)

data class ArBean(
    var name: String,
    var id: Long,
)

data class AlBean(
    var name: String,
    var id: Long,
    var picUrl: String,
)
