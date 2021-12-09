package com.example.wassignment.modules

import com.example.wassignment.remote.ContactsService
import com.example.wassignment.repository.ContactsPagingSource
import com.example.wassignment.utils.Config.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://iie-service-dev.workingllama.com/iie-service/")
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideContactService(retrofit: Retrofit): ContactsService = retrofit.create(ContactsService::class.java)


    @Singleton
    @Provides
    fun provideContactsPagingDataSource(contactsService: ContactsService) =
        ContactsPagingSource(contactsService)

}