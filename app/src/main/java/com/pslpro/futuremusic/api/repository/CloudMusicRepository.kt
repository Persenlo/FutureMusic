package com.pslpro.futuremusic.api.repository

import com.pslpro.futuremusic.api.CloudMusicService
import com.pslpro.futuremusic.api.RetrofitClient

object CloudMusicRepository {

    suspend fun getPlayList(cat:String = "", limit: Int = 10, offset: Int = 0)=
        RetrofitClient.createApi(CloudMusicService::class.java)
            .getPlayList(cat,limit,offset)


}