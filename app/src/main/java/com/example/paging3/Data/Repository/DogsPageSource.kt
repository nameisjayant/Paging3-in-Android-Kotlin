package com.example.paging3.Data.Repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3.Data.Dogs
import com.example.paging3.Data.Network.ApiService
import retrofit2.HttpException
import java.io.IOException

class DogsPageSource
constructor(private val apiService: ApiService) : PagingSource<Int,Dogs>() {

    private val DEFAULT_PAGE_INDEX= 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dogs> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getAllDogs(page,params.loadSize)
            LoadResult.Page(
                response,
                prevKey = if(page == DEFAULT_PAGE_INDEX) null else page-1,
                nextKey = if(response.isEmpty()) null else page+1
            )
        } catch (exception:IOException){
            LoadResult.Error(exception)
        } catch (exception:HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Dogs>): Int? {
     return null
    }

}