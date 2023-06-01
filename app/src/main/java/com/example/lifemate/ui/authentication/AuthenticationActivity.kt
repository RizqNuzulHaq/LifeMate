package com.example.lifemate.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private var _binding: ActivityAuthenticationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, LoginFragment.newInstance())
                .commit()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}