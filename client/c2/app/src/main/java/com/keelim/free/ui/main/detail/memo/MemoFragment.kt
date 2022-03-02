package com.keelim.free.ui.main.detail.memo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keelim.data.model.MemoState
import com.keelim.free.R
import com.keelim.free.databinding.FragmentMemoBinding
import com.keelim.free.util.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoFragment @Inject constructor(
    private val memoId:String
):BottomSheetDialogFragment() {
    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!

    private val memoAdapter by lazy { MemoAdapter() }
    private val viewModel: MemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
        viewModel.init(memoId)
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    private fun initViews() = with(binding){
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        memoRecycler.apply {
            itemAnimator = SpringAddItemAnimator()
            adapter = memoAdapter.apply {
                doOnNextLayout {

                }
            }
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(memoRecycler)
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when (it) {
                    is MemoState.Error -> Unit
                    is MemoState.Loading -> Unit
                    is MemoState.Success -> {
                        if (it.data.isEmpty()) {
                            binding.tvNoData.visibility = View.VISIBLE
                        }
                        memoAdapter.submitList(it.data)
                    }
                    is MemoState.UnInitialized -> Unit
                }
            }
        }
    }
}