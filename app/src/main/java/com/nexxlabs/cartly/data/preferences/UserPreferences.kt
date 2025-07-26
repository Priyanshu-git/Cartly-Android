package com.nexxlabs.cartly.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "cartly_prefs")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    val authToken: Flow<String?> = dataStore.data
        .map { it[PreferenceKeys.TOKEN] }

    suspend fun setAuthToken(token: String) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.TOKEN] = token
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit { prefs ->
            prefs.remove(PreferenceKeys.TOKEN)
        }
    }

    val email: Flow<String?> = dataStore.data
        .map { it[PreferenceKeys.EMAIL] }

    suspend fun setEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.EMAIL] = email
        }
    }
}
