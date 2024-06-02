package com.edwinyosua.ceritaapp.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem
import retrofit2.HttpException

class StoriesPagingSources(private val apiSource: ApiService) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiSource.getStories(page, params.loadSize)

            if (responseData.isSuccessful) {
                val storiesResponseData = responseData.body()?.listStory ?: emptyList()
                val nextKey = if (storiesResponseData.isEmpty()) null else page + 1
                return LoadResult.Page(
                    data = storiesResponseData,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextKey
                )
            } else {
                return LoadResult.Error(HttpException(responseData))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPost ->
            val anchorPage = state.closestPageToPosition(anchorPost)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}