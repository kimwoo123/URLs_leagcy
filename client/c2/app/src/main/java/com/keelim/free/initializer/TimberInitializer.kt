package com.keelim.free.initializer

import android.content.Context
import androidx.startup.Initializer
import com.keelim.free.BuildConfig
import com.keelim.free.util.CrashlyticsTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}