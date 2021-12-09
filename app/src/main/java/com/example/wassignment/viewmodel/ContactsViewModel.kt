package com.example.wassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wassignment.models.Content
import com.example.wassignment.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    application: Application, private val contactsRepository : ContactsRepository
) : AndroidViewModel(application) {

    private var paginatedLiveData: Flow<PagingData<Content>>? = null;
//    private val contactsRepository = ContactsRepository()

    fun fetchPosts(): Flow<PagingData<Content>> {
        if (paginatedLiveData != null)
            return paginatedLiveData as Flow<PagingData<Content>>;
        else {
            paginatedLiveData = contactsRepository.fetchPosts().cachedIn(viewModelScope)
            return contactsRepository.fetchPosts().cachedIn(viewModelScope)
        }
    }

}