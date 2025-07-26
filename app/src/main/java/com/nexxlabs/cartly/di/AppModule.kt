package com.nexxlabs.cartly.di

import android.content.Context
import com.nexxlabs.cartly.data.preferences.SessionManager
import com.nexxlabs.cartly.data.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideSessionManager(userPreferences: UserPreferences): SessionManager {
        return SessionManager(userPreferences)
    }
}
