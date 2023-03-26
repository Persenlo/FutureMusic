package com.pslpro.futuremusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.pslpro.futuremusic.api.paging.CloudPlayListDataSource
import com.pslpro.futuremusic.api.repository.CloudMusicRepository

class NetMusicViewModel: ViewModel() {

    val playList = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 15, // 第一次加载数量
            prefetchDistance = 2,
        )
    ) {
        CloudPlayListDataSource(CloudMusicRepository)
    }.flow.cachedIn(viewModelScope)


}