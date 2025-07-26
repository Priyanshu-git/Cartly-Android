package com.nexxlabs.cartly.data.api

import com.nexxlabs.cartly.data.api.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = "https://your.api.base.url/"

    @Provides
    fun provideAuthInterceptor(): Interceptor = AuthInterceptor {
        // Fetch token from DataStore or SessionManager
        null // replace with actual token logic
    }

    @Provides
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit =
        RetrofitClient.create(baseUrl, client)

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
