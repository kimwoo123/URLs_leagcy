package com.keelim.free.ui.main.recommendation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.keelim.data.model.Recommend
import com.keelim.free.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment(
) : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        val content = arguments?.getString("content", "default")
        val img = arguments?.getString("img", "")
        val circularProgressDrawable = CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 30f
            centerRadius = 30f
            backgroundColor = android.R.color.white
            start()
        }

        ivContent.visibility = View.VISIBLE
        val result = if (URLUtil.isValidUrl(img)) {
            img
        } else {
            "https://k5b201.p.ssafy.io/img/none_pic_pink.fc7e4257.png"
        }
        ivContent.load(result) {
            crossfade(750)
            placeholder(circularProgressDrawable)
        }
        link.text = content

        root.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(content)))
        }
    }


    companion object {
        fun newInstance(item: Recommend): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("content", item.url)
                    putString("img", item.ogImage)
                }
            }
        }
    }
}