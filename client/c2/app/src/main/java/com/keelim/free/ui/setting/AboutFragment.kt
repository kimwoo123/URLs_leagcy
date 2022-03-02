package com.keelim.free.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.keelim.free.R
import com.keelim.free.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        activity?.packageName?.let {
            val packageInfo = context?.packageManager?.getPackageInfo(it, 0)
            val versionName = packageInfo?.versionName
            versionNumberTextView.text = versionName
            if (versionName?.contains(getString(R.string.beta)) == true) {
                releaseChannelTextView.visibility = View.VISIBLE
                releaseChannelTextView.text = getString(R.string.beta)
            }
            if (versionName?.contains(getString(R.string.alpha)) == true) {
                releaseChannelTextView.visibility = View.VISIBLE
                releaseChannelTextView.text = getString(R.string.alpha)
            }
        }
        gitlab.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("")
                ))
        }

        openSourceLicensesCard.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    OssLicensesMenuActivity::class.java
                )
            )
        }
    }
}