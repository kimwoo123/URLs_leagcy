package com.keelim.free.ui.inject

import android.content.Intent
import android.content.Intent.ACTION_PROCESS_TEXT
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.webkit.URLUtil
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme.colors
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.keelim.data.model.Folder
import com.keelim.data.model.UrlState
import com.keelim.free.R
import com.keelim.free.databinding.ActivityInjectBinding
import com.keelim.free.ui.auth.AuthActivity
import com.keelim.free.ui.custome.Titanic
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class InjectActivity : AppCompatActivity() {
    private val binding: ActivityInjectBinding by lazy {
        ActivityInjectBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: InjectViewModel by viewModels()
    private lateinit var result : List<Folder>

    private val token by lazy {
        val pref = getSharedPreferences("token", MODE_PRIVATE)
        pref.getString("token", "")
    }
    val titanic by lazy { Titanic() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenCheck()
        setContentView(binding.root)
        initViews()
        intentControl()
        observeState()
    }

    private fun tokenCheck() {
        token ?: run {
            showToast("로그인을 위한 서비스 입니다.")
            finishAffinity()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    private fun validate():Boolean{
        var flag = true
        with(binding){
            if(URLUtil.isValidUrl(urlField.text.toString()).not()){
                urlField.error = "제대로 된 url 을 입력해주세요"
                flag = false
            }

            if(autoFolders.text.toString().isEmpty()){
                autoFolders.error = "폴더를 설정해주세요"
                flag = false        
            }
        }

        return flag
    }

    private fun initViews() = with(binding) {
        delegate.isHandleNativeActionModesEnabled = false
        tagInject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val line = s.toString()
                if(line.isEmpty()){
                    tags.removeAllViews()
                } else{
                    tags.removeAllViews()
                    val set = line.split(",").map {
                        it.trim()
                    }.toSet()
                    set.forEach { tag ->
                        tags.addView(
                            Chip(this@InjectActivity).apply {
                                text = tag
                                setChipBackgroundColorResource(arrayOf(
                                    R.color.bg_orange, R.color.orange, R.color.orange_w,
                                ).random())
                            })
                    }
                }
            }
        })
        btnSubmit.setOnClickListener{
            titanic.start(tvLoading)
            lifecycleScope.launch{
                delay(1000)
                if(validate()){
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

    private fun intentControl() {
        when (intent?.action) {
            ACTION_SEND -> {
                if (intent.type?.startsWith("text/") == true) {
                    handleText(intent)
                }
            }
            ACTION_PROCESS_TEXT -> {
                if (intent.type?.startsWith("text/") == true) {
                    handleProcessText(intent)
                }
            }
            else -> Unit
        }
    }

    private fun handleText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            binding.injectField.setText(it)
        }
    }

    private fun handleProcessText(intent: Intent) {
        intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.let {
            binding.injectField.setText(it)
        }
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect {
                when(it){
                    is UrlState.Error -> Unit
                    is UrlState.Loading -> Unit
                    is UrlState.Success1 -> {
                        result = it.data

                        val result1 = it.data.map { folder->
                            folder.folder_name
                        }
                        val adapter = ArrayAdapter(this@InjectActivity, R.layout.list_item, result1)
                        binding.autoFolders.setAdapter(adapter)
                    }
                    is UrlState.Success2 -> {
                        startActivity(Intent(this@InjectActivity, AuthActivity::class.java))
                        finish()
                    }
                    is UrlState.UnInitialized -> Unit
                }
            }
        }
    }
}