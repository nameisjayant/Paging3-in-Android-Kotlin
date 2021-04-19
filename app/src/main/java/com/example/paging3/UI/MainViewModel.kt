package com.example.paging3.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging3.Data.Dogs
import com.example.paging3.Data.Network.ApiService
import com.example.paging3.Data.Repository.DogsPageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val apiService: ApiService) : ViewModel()
{
    val getAllDogs:Flow<PagingData<Dogs>> = Pager(config = PagingConfig(20,enablePlaceholders = false)){
        DogsPageSource(apiService)
    }.flow.cachedIn(viewModelScope)


}
