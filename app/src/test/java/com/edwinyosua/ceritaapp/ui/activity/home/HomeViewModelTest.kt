package com.edwinyosua.ceritaapp.ui.activity.home

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.edwinyosua.ceritaapp.DummyData
import com.edwinyosua.ceritaapp.MainDispatcherRule
import com.edwinyosua.ceritaapp.StoriesPagingSourcesTest
import com.edwinyosua.ceritaapp.getOrAwaitValue
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem
import com.edwinyosua.ceritaapp.repository.AppRepository
import com.edwinyosua.ceritaapp.ui.adapter.StoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatchersRules = MainDispatcherRule()

    @Mock
    private lateinit var appRepo: AppRepository

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var mockedLog: MockedStatic<Log>

    @Before
    fun setUp() {
        mockedLog = Mockito.mockStatic(Log::class.java)
        mockedLog.`when`<Boolean> {
            Log.isLoggable(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        }.thenReturn(true)

        val expectedList = MutableLiveData<PagingData<ListStoryItem>>()
        Mockito.`when`(appRepo.getAllStories()).thenReturn(expectedList)
        homeViewModel = HomeViewModel(appRepo)
    }

    @After
    fun tearDown() {
        mockedLog.close()
    }

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyStoriesResponse = DummyData.generateDummyStories()
        val expectedList = MutableLiveData<PagingData<ListStoryItem>>()
        val dataDummy: PagingData<ListStoryItem> =
            StoriesPagingSourcesTest.snapShot(dummyStoriesResponse)

        Mockito.`when`(appRepo.getAllStories()).thenReturn(expectedList)

        expectedList.value = dataDummy


        val homeVm = HomeViewModel(appRepo)
        val actualList: PagingData<ListStoryItem> = homeVm.storiesList.getOrAwaitValue()

        val diff = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        diff.submitData(actualList)
        Assert.assertNotNull(diff.snapshot())
        Assert.assertEquals(dummyStoriesResponse.size, diff.snapshot().size)
        Assert.assertEquals(dummyStoriesResponse[0], diff.snapshot()[0])
    }


    @Test
    fun `when Get List Empty Should Return No Data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedList = MutableLiveData<PagingData<ListStoryItem>>()
        expectedList.value = data
        Mockito.`when`(appRepo.getAllStories()).thenReturn(expectedList)

        val homeVm = HomeViewModel(appRepo)
        val actualList: PagingData<ListStoryItem> = homeVm.storiesList.getOrAwaitValue()
        val diff = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        diff.submitData(actualList)
        Assert.assertEquals(0, diff.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}