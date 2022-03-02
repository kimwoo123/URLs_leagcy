package com.keelim.free.ui.main.dash.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keelim.data.model.DataState
import com.keelim.data.model.topics
import com.keelim.free.databinding.FragmentSearchBinding
import com.keelim.free.util.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        searchResults.apply {
            itemAnimator = SpringAddItemAnimator()
            setHasFixedSize(true)
            adapter = SearchAdapter().apply {
                // add data after layout so that animations run
                doOnNextLayout {
                    submitList(topics)
                }
            }
        }
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when (it) {
                    is DataState.Error -> Unit
                    DataState.Loading -> Unit
                    is DataState.Success -> Unit
                    DataState.UnInitialized -> Unit
                }
            }
        }
    }
}