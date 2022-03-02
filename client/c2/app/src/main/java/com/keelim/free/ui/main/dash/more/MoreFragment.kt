package com.keelim.free.ui.main.dash.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.keelim.data.model.dash.More
import com.keelim.free.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoreViewModel by viewModels()
    private val pointAdapter = MoreAdapter(
        longClick = {
//            dialog(it)
        }
    )
    private val startAdapter = MoreAdapter(
        longClick = {
//            dialog(it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        startRecycler.adapter = startAdapter
        startRecycler.layoutManager = LinearLayoutManager(requireContext())

        pointRecycler.adapter = pointAdapter
        pointRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when (it) {
                    is MoreState.UnInitialized -> handleUnInitialized()
                    is MoreState.Loading -> handleLoading()
                    is MoreState.Success1 -> handleSuccess1(emptyList())
                    is MoreState.Success2 -> handleSuccess2(it.data)
                    is MoreState.Error -> handleError()
                }
            }
        }
    }

    private fun handleUnInitialized() {
        //requireActivity().toast("데이터 초기화 중입니다.")
    }

    private fun handleLoading() {
        //requireActivity().toast("데이터 초기화 중입니다.")
    }

    private fun handleSuccess1(data: List<More>) {
        startAdapter.submitList(data)
    }

    private fun handleSuccess2(data: List<More>) {
        pointAdapter.submitList(data)
    }

    private fun handleError() {
        requireActivity().showToast("에러가 발생했습니다. 다시 한번 로드해주세요")
    }
}