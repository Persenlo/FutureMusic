package com.pslpro.futuremusic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.pslpro.futuremusic.api.paging.CloudPlayListDataSource
import com.pslpro.futuremusic.api.paging.CloudSongsDataSource
import com.pslpro.futuremusic.api.repository.CloudMusicRepository
import com.pslpro.futuremusic.model.PlaylistsBean

class NetMusicViewModel: ViewModel() {

    //当前歌单id
    var choosePlayList = MutableLiveData(PlaylistsBean("",0,"",0,""))

    /**
     * 歌单分页
     */
    val playList = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 15, // 第一次加载数量
            prefetchDistance = 2,
        )
    ) {
        CloudPlayListDataSource(CloudMusicRepository)
    }.flow.cachedIn(viewModelScope)

    /**
     * 歌单歌曲列表分页
     */
    var playListSongs = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 15, // 第一次加载数量
            prefetchDistance = 2,
        )
    ){
        CloudSongsDataSource(CloudMusicRepository,choosePlayList.value!!.id)
    }.flow.cachedIn(viewModelScope)


}