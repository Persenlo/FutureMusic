package com.pslpro.futuremusic.api

import com.pslpro.futuremusic.model.NetPlayListModel
import com.pslpro.futuremusic.model.PlaylistSongsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CloudMusicService {

    /**
     * 获取歌单
     */
    @GET("top/playlist")
    suspend fun getPlayList(
        @Query("cat") cat: String = "",
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): NetPlayListModel

    /**
     * 获取歌单详情
     */
    @GET("playlist/track/all")
    suspend fun getPlayListSongs(
        @Query("id") id: Long ,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): PlaylistSongsModel
}