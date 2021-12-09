package com.example.wassignment.repository

import androidx.paging.PagingSource
import com.example.wassignment.models.Content
import com.example.wassignment.remote.ContactsService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1


class ContactsPagingSource @Inject constructor(
    private val contactsService: ContactsService,
) :
    PagingSource<Int, Content>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {

        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = contactsService.fetchContacts(page = pageIndex)
            val contactsList = response.body()?.content

            val nextKey =
                if (contactsList?.isEmpty() == true) {
                    null
                } else {
                    pageIndex + (params.loadSize / NETWORK_START_INDEX)
                }
            LoadResult.Page(
                contactsList ?: listOf(),
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}