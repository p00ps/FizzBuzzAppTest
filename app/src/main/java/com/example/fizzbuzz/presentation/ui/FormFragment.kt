package com.example.fizzbuzz.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fizzbuzz.databinding.FormFragmentBinding
import com.example.fizzbuzz.presentation.viewmodel.MainViewModel

class FormFragment : Fragment() {

    private val view by lazy { FormFragmentBinding.inflate(layoutInflater) }
    private val viewModel : MainViewModel by viewModels()

    companion object {
        fun newInstance() = FormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return view.root
    }
}