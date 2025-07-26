package com.nexxlabs.cartly.data.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val TOKEN = stringPreferencesKey("auth_token")
    val EMAIL = stringPreferencesKey("user_email")
}
