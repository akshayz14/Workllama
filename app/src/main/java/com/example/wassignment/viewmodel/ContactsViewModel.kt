package com.example.wassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wassignment.models.Content
import com.example.wassignment.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private var paginatedLiveData: Flow<PagingData<Content>>? = null;
    private val contactsRepository = ContactsRepository(application)

    fun fetchPosts(): Flow<PagingData<Content>> {
        if (paginatedLiveData != null)
            return paginatedLiveData as Flow<PagingData<Content>>;
        else {
            paginatedLiveData = contactsRepository.fetchPosts().cachedIn(viewModelScope)
            return contactsRepository.fetchPosts().cachedIn(viewModelScope)
        }
    }

}