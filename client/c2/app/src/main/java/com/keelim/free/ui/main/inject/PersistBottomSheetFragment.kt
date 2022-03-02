package com.keelim.free.ui.main.inject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ArrayAdapter
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.keelim.data.model.Folder
import com.keelim.data.model.UrlState
import com.keelim.free.R
import com.keelim.free.databinding.LayoutCollapseBinding
import com.keelim.free.databinding.LayoutExpandBinding
import com.keelim.free.ui.custome.Titanic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.co.prnd.persistbottomsheetfragment.BottomSheetCallback
import kr.co.prnd.persistbottomsheetfragment.databinding.FragmentPersistBottomSheetBinding

@AndroidEntryPoint
class PersistBottomSheetFragment(
    @LayoutRes private val collapseResId: Int = R.layout.layout_collapse,
    @LayoutRes private val expandResId: Int = R.layout.layout_expand,
    private val heightType: HeightType = HeightType.WRAP,
) : Fragment() {

    private var _binding: FragmentPersistBottomSheetBinding? = null

    @Suppress("MemberVisibilityCanBePrivate")
    private val binding: FragmentPersistBottomSheetBinding
        get() = _binding ?: throw IllegalAccessException("Can not access destroyed view")

    @Suppress("MemberVisibilityCanBePrivate")
    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.flContainer) }

    @Suppress("MemberVisibilityCanBePrivate")
    private lateinit var LayoutCollapseBinding: LayoutCollapseBinding

    @Suppress("MemberVisibilityCanBePrivate")
    private lateinit var LayoutExpandBinding: LayoutExpandBinding

    @Suppress("MemberVisibilityCanBePrivate")
    val isExpanded: Boolean
        get() = when (bottomSheetBehavior.state) {
            BottomSheetBehavior.STATE_EXPANDED,
            BottomSheetBehavior.STATE_HALF_EXPANDED,
            BottomSheetBehavior.STATE_SETTLING,
            -> true
            else -> false
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPersistBottomSheetBinding.inflate(inflater, container, false).apply {
            LayoutCollapseBinding = DataBindingUtil.inflate(inflater,
                collapseResId,
                viewCollapseContainer,
                true)
            LayoutExpandBinding = DataBindingUtil.inflate(inflater,
                expandResId,
                viewExpandContainer,
                true)

            viewContent.layoutParams.height = heightType.layoutParamHeight
        }
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetBehavior()
        LayoutCollapseBinding.init()
        LayoutExpandBinding.init()
        initViewsCollapse()
        initViewsExpand()
        observeState()
    }

    private fun ViewDataBinding.init() {
        lifecycleOwner = this@PersistBottomSheetFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBottomSheetBehavior() = with(bottomSheetBehavior) {
        val bottomSheetCallback =
            BottomSheetCallback(
                this,
                binding.root,
                binding.flContainer,
                binding.viewCollapseContainer,
                binding.viewExpandContainer,
            )
        addBottomSheetCallback(bottomSheetCallback)

        LayoutCollapseBinding.root.doOnLayout {
            peekHeight = it.height
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun expand() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun collapse() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun handleBackKeyEvent() =
        when {
            childFragmentManager.backStackEntryCount > 0 -> {
                childFragmentManager.popBackStackImmediate()
                true
            }
            isExpanded -> {
                collapse()
                true
            }
            else -> false
        }

    fun changeHeightType(heightType: HeightType) {
        binding.viewContent.layoutParams.height = heightType.layoutParamHeight
    }

    enum class HeightType(val layoutParamHeight: Int) {
        WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),
        MATCH(ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private val viewModel: Inject2ViewModel by viewModels()
    private lateinit var result: List<Folder>

    val titanic by lazy { Titanic() }

    private fun initViewsCollapse() = with(LayoutCollapseBinding){
        viewSelect.setOnClickListener {
            expand()
        }
    }


    private fun initViewsExpand() = with(LayoutExpandBinding) {
        tagInject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val line = s.toString()
                if (line.isEmpty()) {
                    tags.removeAllViews()
                } else {
                    tags.removeAllViews()
                    val set = line.split(",").map {
                        it.trim()
                    }.toSet()
                    set.forEach { tag ->
                        if (tag.isEmpty()) return@forEach
                        tags.addView(
                            Chip(requireActivity()).apply {
                                text = tag
                                setChipBackgroundColorResource(
                                    arrayOf(
                                        R.color.bg_orange, R.color.orange, R.color.orange_w,
                                    ).random()
                                )
                            })
                    }
                }
            }
        })
        btnSubmit.setOnClickListener {
            titanic.start(tvLoading)
            lifecycleScope.launch {
                delay(1000)
                if (validate()) {
                    urlField.error = null
                    injectField.error = null
                    autoFolders.error = null
                    tagInject.error = null

                    viewModel.submitUrl(
                        urlField.text.toString(),
                        injectField.text.toString(),
                        result.find { it.folder_name == autoFolders.text.toString() }!!.folder_id,
                        tagInject.text.toString()
                    )
                }
            }
        }
    }


    private fun validate(): Boolean {
        var flag = true
        with(LayoutExpandBinding) {
            if (URLUtil.isValidUrl(urlField.text.toString()).not()) {
                urlField.error = "제대로 된 url 을 입력해주세요"
                flag = false
            }

            if (autoFolders.text.toString().isEmpty()) {
                autoFolders.error = "폴더를 설정해주세요"
                flag = false
            }
        }

        return flag
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when (it) {
                    is UrlState.Error -> Unit
                    is UrlState.Loading -> Unit
                    is UrlState.Success1 -> {
                        result = it.data

                        val result1 = it.data.map { folder ->
                            folder.folder_name
                        }
                        val adapter = ArrayAdapter(requireActivity(), R.layout.list_item, result1)
                        LayoutExpandBinding.autoFolders.setAdapter(adapter)
                    }
                    is UrlState.Success2 -> {
                        handleBackKeyEvent()
                        titanic.cancel()
                        Snackbar.make(binding.root, "정상적으로 저장되었습니다", Snackbar.LENGTH_SHORT).show()
                    }
                    is UrlState.UnInitialized -> Unit
                }
            }
        }
    }


    companion object {
        private val TAG = PersistBottomSheetFragment::class.simpleName
        fun show(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
        ): PersistBottomSheetFragment =
            fragmentManager.findFragmentByTag(TAG) as? PersistBottomSheetFragment
                ?: PersistBottomSheetFragment().apply {
                    fragmentManager.beginTransaction()
                        .replace(containerViewId, this, TAG)
                        .commitAllowingStateLoss()
                }
    }
}
