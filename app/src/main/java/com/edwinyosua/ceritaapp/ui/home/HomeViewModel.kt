package com.edwinyosua.ceritaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem
import com.edwinyosua.ceritaapp.repository.AppsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val appRepo: AppsRepository) : ViewModel() {


    val storiesList: LiveData<PagingData<ListStoryItem>> =
    appRepo.getAllStories().cachedIn(viewModelScope)

    fun logoutUser() {
        viewModelScope.launch {
            appRepo.clearToken()
        }
    }

}