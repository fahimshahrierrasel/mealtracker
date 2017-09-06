package com.fahimshahrierrasel.mealtracker.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * Created by fahim on 9/5/17.
 * Project: MealTracker
 */
class CaptureImage(var context: Context) {
    private val REQUEST_IMAGE_CAPTURE = 111

    fun onLaunchCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(context.packageManager) != null) {
            (context as Activity).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    fun encodeBitmapAndSaveToRealm(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    @Throws(IOException::class)
    fun decodeFromBase64Image(image: String): Bitmap {
        val decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }
}