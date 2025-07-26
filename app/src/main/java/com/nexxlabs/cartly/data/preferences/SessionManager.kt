package com.nexxlabs.cartly.data.preferences

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val userPreferences: UserPreferences
) {

    val authToken: Flow<String?> = userPreferences.authToken

    suspend fun saveAuthToken(token: String) {
        userPreferences.setAuthToken(token)
    }

    suspend fun clearSession() {
        userPreferences.clearAuthToken()
    }

}
