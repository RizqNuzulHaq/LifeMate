package com.example.lifemate.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.lifemate.ui.main.MainActivity
import com.example.lifemate.data.response.LoginResponse
import com.example.lifemate.data.retrofit.ApiConfig
import com.example.lifemate.databinding.ActivityLoginBinding
import com.example.lifemate.model.UserModel
import com.example.lifemate.model.UserPreference
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) {user ->
            this.user = user
        }
    }

    private fun setupAction(){
        setMyButtonEnable()

        binding!!.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding!!.btnLogin.setOnClickListener{
            val email = binding!!.edtEmail.text.toString()
            val password = binding!!.edtPassword.text.toString()
            val apiService = ApiConfig().getApiService()

            when{
                email.isEmpty() -> {binding!!.edtlEmail.error = "Masukkan Email"}
                password.isEmpty() -> {binding!!.edtlPassword.error = "Masukkan Pasword"}

                else ->{
                    binding!!.progressBar.visibility = View.VISIBLE
                    val loginRequest = apiService.login(email, password)

                    loginRequest.enqueue(object: Callback<LoginResponse>{
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if(response.isSuccessful){
                                binding!!.progressBar.visibility = View.GONE

                                val responseBody = response.body()
                                if(responseBody != null && responseBody.message.equals("Login successful")){
                                    val token = responseBody.token
                                    val idUser = responseBody.idUser
                                    loginViewModel.login(token)

                                    Toast.makeText(this@LoginActivity, "Login Succesfull", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }else{
                                binding!!.progressBar.visibility = View.GONE
                                Toast.makeText(this@LoginActivity, "password atau email salah", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            binding!!.progressBar.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }

        binding!!.tvRegister.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setMyButtonEnable(){
        val email = binding!!.edtEmail.text
        binding!!.btnLogin.isEnabled = email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}