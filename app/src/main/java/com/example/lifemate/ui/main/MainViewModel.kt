package com.example.lifemate.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifemate.model.UserModel
import com.example.lifemate.model.UserPreference
import kotlinx.coroutines.launch

class MainViewModel (private val pref: UserPreference) : ViewModel(){
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout(){
        viewModelScope.launch{
            pref.logout()
        }
    }
}