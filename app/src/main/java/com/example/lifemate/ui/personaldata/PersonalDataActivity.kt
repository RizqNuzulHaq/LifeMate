package com.example.lifemate.ui.personaldata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityMainBinding
import com.example.lifemate.databinding.ActivityPersonalDataBinding

class PersonalDataActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var _binding: ActivityPersonalDataBinding? = null
    private val binding get() = _binding!!
    private var genderText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, gender)
        binding.genderSpinner.adapter = arrayAdapter
        binding.genderSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        genderText = parent?.getItemAtPosition(position).toString()
        //Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}