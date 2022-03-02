package com.keelim.free.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keelim.free.MyApplication
import com.keelim.free.databinding.ActivityAuthBinding
import com.keelim.free.ui.main.MenuActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json.Default.context
import showToast
import timber.log.Timber
import java.util.concurrent.Executor

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )
    private val viewModel: AuthViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    private val token by lazy {
        val pref = getSharedPreferences("token", MODE_PRIVATE)
        pref.getString("token", "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBio()
        tokenCheck()
        observe()
        initViews()
    }

    private fun tokenCheck() {
        Firebase.auth.currentUser?.let{
            viewModel.setTokenCheck()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Timber.d("onSignInResult: ${response?.idpToken}")
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val pref = getSharedPreferences("token", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.apply{
                putString("token", response?.idpToken)
                apply()
            }

            startActivity(Intent(this@AuthActivity, MenuActivity::class.java).apply {
                putExtra("token", response?.idpToken)
            })
            finish()
        } else {
            Timber.e("onSignInResult ${response?.error}")
        }
    }

    private fun signIn() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun initViews() = with(binding) {
        val tv = signInButton.getChildAt(0) as (android.widget.TextView)
        tv.text = "Please Add Google Login"
        signInButton.setOnClickListener {
            logout()
            signIn()
        }

        authTitle.setOnClickListener {
            token.let {
                viewModel.tokenCheck(it!!)
            }
        }

        btnFinger.setOnClickListener {
            val pref = getSharedPreferences("token", MODE_PRIVATE)
            val token = pref.getString("token", "")
            if (token != "") {
                biometricPrompt.authenticate(
                    BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric login for my app")
                        .setSubtitle("Log in using your biometric credential")
                        .setNegativeButtonText("Use account password")
                        .build()
                )
            } else {
                Snackbar.make(binding.root, "최초 인증은 간편로그인을 사용하시기 바랍니다.", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
            }
    }

    private fun initBio() {
        executor = ContextCompat.getMainExecutor(applicationContext)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        this@AuthActivity,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@AuthActivity, MenuActivity::class.java))
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Snackbar.make(binding.root, "인증을 실패하였습니다.", Snackbar.LENGTH_SHORT).show()
                }
            })
    }

    private fun observe() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.token.collect {
                if (it) {
                    startActivity(Intent(this@AuthActivity, MenuActivity::class.java))
                    finish()
                } else {
                    showToast("토큰이 존재하지 않습니다.")
                }
            }
        }
    }
}
