package com.yondikavl.scancer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

object Utils {
    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }
}
