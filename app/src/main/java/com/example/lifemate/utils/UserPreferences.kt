package com.example.lifemate.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.lifemate.ui.authentication.UserViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val token = stringPreferencesKey("Token")
    private val uid = intPreferencesKey("UID")
    private val isLogin = booleanPreferencesKey("false")

    fun getUser(): Flow<Any>{
        return dataStore.data.map {
            it[token] ?: "token"
            it[isLogin] ?: false
        }
    }

    suspend fun login(token: String){
        dataStore.edit {
            it[this.token] = token
            it[isLogin] = true
        }
    }

    fun getUserToken(): Flow<String> = dataStore.data.map { it[token] ?: "token" }

    suspend fun saveUserPref(tokenUser:String, uidUser:Int){
        dataStore.edit { preferences ->
            preferences[token] = tokenUser
            preferences[uid] = uidUser
        }
    }

    suspend fun clearUserPref() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}