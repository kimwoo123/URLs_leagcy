package com.keelim.core.util

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ImageUriProvider {
    suspend operator fun invoke(url: String): Uri
    suspend operator fun invoke(file: File): Uri
    suspend operator fun invoke(bitmap: Bitmap): Uri
}