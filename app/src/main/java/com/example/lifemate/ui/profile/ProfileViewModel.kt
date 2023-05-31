package com.example.lifemate.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemate.utils.UserPreferences
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun clearUserPref() {
        viewModelScope.launch {
            pref.clearUserPref()
        }
    }
}