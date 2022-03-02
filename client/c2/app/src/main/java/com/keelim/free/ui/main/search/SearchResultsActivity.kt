package com.keelim.free.ui.main.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.keelim.core.extensions.showToast
import com.keelim.free.databinding.ActivitySearchResultsBinding
import com.keelim.free.ui.main.dash.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultsActivity : AppCompatActivity() {
    private val binding: ActivitySearchResultsBinding by lazy {
        ActivitySearchResultsBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            viewModel.search(query!!)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}