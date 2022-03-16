package com.example.fizzbuzz.presentation.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fizzbuzz.core.processEvent
import com.example.fizzbuzz.databinding.ResultFragmentBinding
import com.example.fizzbuzz.presentation.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private val binding by lazy { ResultFragmentBinding.inflate(layoutInflater) }
    private val viewModel: ResultViewModel by viewModels()
    private val resultAdapter = ResultAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        processEvent(viewModel, ::renderEvent)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.sendIntent(ResultViewModel.ViewAction.Reset)
            findNavController().navigateUp()
        }

        binding.recycler.apply {
            setHasFixedSize(true)
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.recycler.visibility = View.VISIBLE
        }

        lifecycleScope.launchWhenResumed {
            viewModel.sendIntent(ResultViewModel.ViewAction.GetFirstRange)
        }
    }

    private fun renderEvent(event: ResultViewModel.ViewEvent) {
        when (event) {
            is ResultViewModel.ViewEvent.FizzBizzRangeResult -> {
                resultAdapter.updateList(items = event.result)
                binding.progressbar.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
            else -> {}
        }
    }
}