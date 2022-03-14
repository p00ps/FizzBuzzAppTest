package com.example.fizzbuzz.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.fizzbuzz.R
import com.example.fizzbuzz.databinding.FormFragmentBinding
import com.example.fizzbuzz.presentation.viewmodel.MainViewModel

class FormFragment : Fragment() {

    private val binding by lazy { FormFragmentBinding.inflate(layoutInflater) }
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.validate.setOnClickListener {
            view.findNavController().navigate(R.id.action_baseFormFragment_to_resultFragment)
        }
    }
}