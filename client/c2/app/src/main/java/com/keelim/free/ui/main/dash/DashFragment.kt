package com.keelim.free.ui.main.dash

import android.content.Intent
import android.net.Uri
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
import androidx.navigation.fragment.findNavController
import com.keelim.data.model.dash.DashState
import com.keelim.free.R
import com.keelim.free.databinding.FragmentDashBinding
import com.keelim.free.util.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class DashFragment : Fragment() {
    private var _binding: FragmentDashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashViewModel by viewModels()
    private val recommendAdapter by lazy {
        RecommendAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    private fun initViews() = with(binding) {
        recommendRecycler.apply {
            itemAnimator = SpringAddItemAnimator()
            adapter = recommendAdapter.apply {
                doOnNextLayout {

                }
            }
        }
        btnHome.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://k5b201.p.ssafy.io")))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when (it) {
                    is DashState.Error -> {
                    }
                    is DashState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    is DashState.Success -> {
                        binding.descSection1.text = it.data.urls.toString()
                        binding.descSection2.text = it.data.folders.toString()
                    }
                    is DashState.Success2 -> {
                        recommendAdapter.submitList(it.data)
                        binding.progress.visibility = View.INVISIBLE
                    }
                    is DashState.UnInitialized -> Unit
                }
            }
        }
    }
}