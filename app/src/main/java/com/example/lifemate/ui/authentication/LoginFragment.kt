package com.example.lifemate.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifemate.MainActivity
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentLoginBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.customview.EmailEditText


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel by viewModels<AuthViewModel>()
    private val userViewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.loginResult.observe(viewLifecycleOwner) {
            userViewModel.saveUserPref(
                it.token,
                it.idUser
            )
        }

        authViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        userViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            if (token != "token") {

                Intent(requireActivity(), MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPass.text.toString()

            val isValidEmail = validateEmail(email)
            val isValidPassword = validatePassword(password)

            if (isValidEmail && isValidPassword) {
                Log.d("test", "test")
                authViewModel.loginResponse(email, password)
            }

        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            binding.edtlEmail.error = "Email cannot be empty"
            return false
        }
        if (!email.matches(EmailEditText.emailPattern)) {
            return false
        }
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            binding.edtlPass.error = "Password cannot be empty"
            return false
        }
        if (password.length < 6) {
            return false
        }
        return true
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}