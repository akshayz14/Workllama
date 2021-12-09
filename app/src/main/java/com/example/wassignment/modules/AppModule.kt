package com.example.wassignment.modules

import android.content.Context
import com.example.wassignment.ContactDetailsFragment
import com.example.wassignment.adapter.ContactsAdapter
import com.example.wassignment.remote.ContactsService
import com.example.wassignment.repository.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        contactsService: ContactsService
    ) = ContactsRepository(contactsService)


    @Provides
    @Singleton
    fun provideAdapter() = ContactsAdapter()

}