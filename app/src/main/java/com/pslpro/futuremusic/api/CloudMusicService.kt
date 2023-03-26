package com.pslpro.futuremusic.api

import com.pslpro.futuremusic.model.NetPlayListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CloudMusicService {

    @GET("top/playlist")
    suspend fun getPlayList(
        @Query("cat") cat: String = "",
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): NetPlayListModel
}