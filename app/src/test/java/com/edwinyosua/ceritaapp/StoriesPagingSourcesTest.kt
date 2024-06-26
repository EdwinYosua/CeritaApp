package com.edwinyosua.ceritaapp

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem

class StoriesPagingSourcesTest : PagingSource<Int, LiveData<ListStoryItem>>() {

    companion object {
        fun snapShot(item: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(item)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<ListStoryItem>>): Int = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<ListStoryItem>> =
        LoadResult.Page(emptyList(), 0, 1)


}