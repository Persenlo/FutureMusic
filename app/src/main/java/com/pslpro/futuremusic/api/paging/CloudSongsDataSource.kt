package com.pslpro.futuremusic.api.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pslpro.futuremusic.api.repository.CloudMusicRepository
import com.pslpro.futuremusic.model.SongsBean

class CloudSongsDataSource(private val repository: CloudMusicRepository, val playListId: Long): PagingSource<Int, SongsBean>() {
    private val TAG = "CloudSongsDataSource"

    override fun getRefreshKey(state: PagingState<Int, SongsBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SongsBean> {
        return try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize

            // 每一页请求几条数据
            val everyPageSize = 10
            // 第一次初始请求，多加载一点
            val initPageSize = 15
            // 当前请求的起始位置，指起始下标
            val curStartItem =
                if (currentPage == 1) 1 else (currentPage - 2) * everyPageSize + 1 + initPageSize

            val responseList = repository.getPlayListSongs(playListId,everyPageSize,curStartItem).songs
            // 上一页页码
            val preKey = if (currentPage == 1) null else currentPage.minus(1)
            // 下一页页码
            var nextKey: Int? = currentPage.plus(1)
            Log.d(TAG, "currentPage: $currentPage")
            Log.d(TAG, "preKey: $preKey")
            Log.d(TAG, "nextKey: $nextKey")
            if (responseList.isEmpty()) {
                nextKey = null
            }

            LoadResult.Page(
                data = responseList,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}