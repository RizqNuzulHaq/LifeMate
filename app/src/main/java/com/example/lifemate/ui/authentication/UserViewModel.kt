package com.example.lifemate.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifemate.utils.UserPreferences
import kotlinx.coroutines.launch

class UserViewModel (private val pref: UserPreferences): ViewModel(){
    fun saveUserPref(userToken: String, userUid: Int) {
        viewModelScope.launch {
            pref.saveUserPref(userToken,userUid)
        }
    }

    fun clearUserPref() {
        viewModelScope.launch {
            pref.clearUserPref()
        }
    }

    fun getUserToken() : LiveData<String>{
        return pref.getUserToken().asLiveData()
    }

}