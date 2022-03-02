package com.keelim.free.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.keelim.free.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ServiceFragment @Inject constructor(

):Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://k5b201.p.ssafy.io/")))
        findNavController().navigate(R.id.nav_home)
    }
}