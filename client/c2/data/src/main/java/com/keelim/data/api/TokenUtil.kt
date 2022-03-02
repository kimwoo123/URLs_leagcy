package com.keelim.data.api

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import timber.log.Timber

class TokenUtil @Inject constructor(
    @ApplicationContext private val ctx: Context
) {
    fun provideToken(): String? {
        val pref = ctx.getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE)
        val token = pref.getString("token", "")
        Timber.d("this is token $token")
        return token
    }
}