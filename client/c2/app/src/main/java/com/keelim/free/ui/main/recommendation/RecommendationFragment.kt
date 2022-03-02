package com.keelim.free.ui.main.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.keelim.data.model.MainState
import com.keelim.data.model.Recommend
import com.keelim.free.databinding.FragmentRecommendationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendationFragment : Fragment() {
    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: ViewPager2Adapter
    private val viewModel: RecommendationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    private fun initViews() = with(binding) {
        viewPagerAdapter = ViewPager2Adapter()
        viewpager.adapter = viewPagerAdapter

        viewpager.adapter = viewPagerAdapter
        TabLayoutMediator(tabIndicator, viewpager) { tab, position ->
        }.attach()
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.state.collect {
                when (it) {
                    is MainState.UnInitialized -> {
                        Snackbar.make(binding.root, "데이터를 불러오고 있습니다. ", Snackbar.LENGTH_SHORT)
                            .show()
                        viewModel.init()
                        binding.progress.visibility = View.VISIBLE
                    }
                    is MainState.Loading -> {
                        viewPagerAdapter.setData(emptyList())
                    }
                    is MainState.Success -> {
                        binding.progress.visibility = View.INVISIBLE
                        if (viewModel.basic.value!!.isNotEmpty()) {
                            viewPagerAdapter.setData(viewModel.basic.value!!)
                        } else {
                            viewPagerAdapter.setData(it.data)
                        }
                    }
                    is MainState.Error -> {
                        binding.progress.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }


    inner class ViewPager2Adapter : FragmentStateAdapter(this) {
        private var fragments = listOf<Fragment>()

        fun setData(data: List<Recommend>) {
            fragments = data.map {
                DetailFragment.newInstance(it)
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemId(position: Int): Long {
            return fragments[position].hashCode().toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return fragments.any {
                it.hashCode().toLong() == itemId
            }
        }
    }
}