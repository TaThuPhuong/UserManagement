package com.ttp.usermanagement.common.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ttp.usermanagement.common.util.Constants.ID_KEY
import com.ttp.usermanagement.common.util.Constants.JWT_TOKEN_KEY
import com.ttp.usermanagement.common.util.Constants.NAME_KEY
import kotlinx.coroutines.flow.first

private const val USER_PREFERENCES_NAME = "session_manager"

class SessionManager(val context: Context) {
    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(USER_PREFERENCES_NAME)


    suspend fun updateSession(token: String, userID: String, fullName: String) {
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        val idKey = stringPreferencesKey(ID_KEY)
        val nameKey = stringPreferencesKey(NAME_KEY)
        context.dataStore.edit { preferences ->
            preferences[jwtTokenKey] = token
            preferences[idKey] = userID
            preferences[nameKey] = fullName
        }
    }


    suspend fun getJwtToken(): String? {
        val jwtTokenKey = stringPreferencesKey(JWT_TOKEN_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[jwtTokenKey]
    }

    suspend fun getNameUserLogin(): String? {
        val nameKey = stringPreferencesKey(NAME_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[nameKey]
    }


    suspend fun getCurrentUserLogin(): String? {
        val nameKey = stringPreferencesKey(ID_KEY)
        val preferences = context.dataStore.data.first()
        return preferences[nameKey]
    }

    suspend fun logout() {
        context.dataStore.edit {
            it.clear()
        }
    }
}