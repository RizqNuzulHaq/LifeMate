package com.example.lifemate.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentLoginBinding
import com.example.lifemate.databinding.FragmentProfileBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.ui.main.MainActivity
import com.example.lifemate.ui.personaldata.PersonalDataActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPersonalData.setOnClickListener {
            Intent(requireActivity(), PersonalDataActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnLogout.setOnClickListener{
            profileViewModel.clearUserPref()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}