package com.teampophory.pophory.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPophoryDataStore @Inject constructor(
    private val preferences: SharedPreferences
) : PophoryDataStore {
    override var accessToken: String
        get() = preferences.getString("access_token", "").orEmpty()
        set(value) {
            preferences.edit {
                putString("access_token", value)
            }
        }
    override var refreshToken: String
        get() = preferences.getString("refresh_token", "").orEmpty()
        set(value) {
            preferences.edit {
                putString("refresh_token", value)
            }
        }
    override var userName: String
        get() = preferences.getString("user_name", "").orEmpty()
        set(value) {
            preferences.edit {
                putString("user_name", value)
            }
        }
    override var userId: String
        get() = preferences.getString("user_id", "").orEmpty()
        set(value) {
            preferences.edit {
                putString("user_id", value)
            }
        }
}