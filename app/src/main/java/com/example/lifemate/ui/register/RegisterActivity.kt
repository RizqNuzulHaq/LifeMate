package com.example.lifemate.ui.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.lifemate.R
import com.example.lifemate.data.response.RegisterResponse
import com.example.lifemate.data.retrofit.ApiConfig
import com.example.lifemate.databinding.ActivityRegisterBinding
import com.example.lifemate.model.UserModel
import com.example.lifemate.model.UserPreference
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]
    }

    private fun setupAction(){
        setDate()
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

        binding!!.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
                if(s.toString().isNotEmpty()){
                    if(s.toString().length < 8){
                        binding!!.edtlPassword.setPasswordVisibilityToggleEnabled(false)
                    }else{
                        binding!!.edtlPassword.setPasswordVisibilityToggleEnabled(true)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding!!.btnRegister.setOnClickListener{
            val name = binding!!.edtName.text.toString()
            val email = binding!!.edtEmail.text.toString()
            val password = binding!!.edtPassword.text.toString()
            val birthDate = binding!!.edtBirthdate.text.toString()
            val gender = binding!!.edtGender.text.toString()
            val apiService = ApiConfig().getApiService()

            when{
                name.isEmpty() -> binding!!.edtName.error = "Silahkan masukkan nama terlebih dahulu"
                email.isEmpty() -> binding!!.edtlEmail.error = "Silahkan masukkan email terlebih dahulu"
                password.isEmpty() -> binding!!.edtlPassword.error = "Silahkan masukkan password terlebih dahulu"
                birthDate.isEmpty() -> binding!!.edtlBirthdate.error = "Silahkan Masukkan tanggal lahir terlebih dahulu"
                gender.isEmpty() -> binding!!.edtlGender.error = "Silahakan masukkan gender terbelih dahulu"

                else ->{
                    binding!!.progressBar.visibility = View.VISIBLE
                    val registerRequest = apiService.register(name, email, password, birthDate, gender)

                    registerRequest.enqueue(object: Callback<RegisterResponse>{
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if(response.isSuccessful){
                                binding!!.progressBar.visibility = View.GONE
                                val responseBody = response.body()

                                if(responseBody != null && responseBody.message.equals("User Created")){
                                    registerViewModel.saveUser(UserModel(name, "", false))
                                    AlertDialog.Builder(this@RegisterActivity).apply {
                                        setTitle("Yeah!")
                                        setMessage("Akun anda berhasil dibuat.")
                                        setPositiveButton("Lanjut"){_,_ ->
                                            val intent = Intent(context, LoginActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(intent)
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            }else{
                                binding!!.progressBar.visibility = View.GONE
                                Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            binding!!.progressBar.visibility = View.GONE
                            Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }

        binding!!.tvRegister.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setDate(){
        var calendar = Calendar.getInstance()

        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        val datepicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(calendar)
        }

        binding!!.edtBirthdate.setOnClickListener{
            DatePickerDialog(this, datepicker, year, month, date).show()
        }
    }

    private fun updateLable(calendar: Calendar){
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding!!.edtBirthdate.setText(sdf.format(calendar.time))
    }

    private fun setMyButtonEnable(){
        val email = binding!!.edtEmail.text
        val pw = binding!!.edtPassword.text
        binding!!.btnRegister.isEnabled = pw != null && pw.toString().length >7 && email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}