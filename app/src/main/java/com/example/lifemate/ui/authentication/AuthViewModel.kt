package com.example.lifemate.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.LoginResponse
import com.example.lifemate.data.response.RegisterResponse
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel: ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun loginResponse(email: String,password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message.equals("Login successful")){
                        _loginResult.postValue(response.body())
                        _isError.value = "Login Berhasil"
                    }
                    else{
//                        _isError.value = "ERROR ${response.code()} : ${response.message()}"
                        _isError.value = "password atau email salah"
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.postValue(t.message)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}