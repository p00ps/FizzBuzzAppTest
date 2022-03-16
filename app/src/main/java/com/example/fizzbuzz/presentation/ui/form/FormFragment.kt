package com.example.fizzbuzz.presentation.ui.form

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fizzbuzz.R
import com.example.fizzbuzz.core.processEvent
import com.example.fizzbuzz.databinding.FormFragmentBinding
import com.example.fizzbuzz.presentation.model.UserInput
import com.example.fizzbuzz.presentation.viewmodel.FormViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormFragment : Fragment() {

    private val binding by lazy { FormFragmentBinding.inflate(layoutInflater) }
    private val viewModel: FormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.validate.setOnClickListener {
            viewModel.sendIntent(
                FormViewModel.ViewAction.CheckInput(
                    UserInput(
                        numberInputOne = binding.edittext1.text.toString().toInt(),
                        numberInputTwo = binding.edittext2.text.toString().toInt(),
                        stringInputOne = binding.edittext3.text.toString(),
                        stringInputTwo = binding.edittext4.text.toString(),
                        limitInput = binding.edittext5.text.toString().toInt(),
                    )
                )
            )
        }

        processEvent(viewModel, ::renderEvent)
    }

    private fun renderEvent(event: FormViewModel.ViewEvent) {
        when (event) {
            is FormViewModel.ViewEvent.InputNotValid -> showError()
            FormViewModel.ViewEvent.GoToResultList -> findNavController().navigate(R.id.action_baseFormFragment_to_resultFragment)
        }
    }

    private fun showError() {
        binding.root.clearFocus()
        val imm = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        Snackbar.make(
            binding.root,
            getString(R.string.input_form_error),
            Snackbar.LENGTH_LONG
        ).show()
    }
}