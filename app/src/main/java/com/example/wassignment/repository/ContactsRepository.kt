package com.example.wassignment.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.wassignment.models.Content
import com.example.wassignment.remote.ContactsClient
import com.example.wassignment.remote.ContactsService
import kotlinx.coroutines.flow.Flow

const val NETWORK_START_INDEX = 10

class ContactsRepository(context: Context) {
    private val contactsService = ContactsClient.getClient().create(ContactsService::class.java)

    @OptIn(ExperimentalPagingApi::class)
    fun fetchPosts(): Flow<PagingData<Content>> {
        return Pager(
            PagingConfig(pageSize = NETWORK_START_INDEX, enablePlaceholders = false, prefetchDistance = 3),
            pagingSourceFactory = { ContactsPagingSource(contactsService)}
        ).flow
    }
}