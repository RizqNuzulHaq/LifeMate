package com.example.lifemate.di

import android.content.Context
import com.example.lifemate.utils.UserPreferences
import com.example.lifemate.utils.dataStore

object Injection {
    fun providePreferences(context: Context): UserPreferences {
        return UserPreferences.getInstance(context.dataStore)
    }
}