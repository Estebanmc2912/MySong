package com.masglobal.mysong.ui.main.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.*
import java.io.ByteArrayOutputStream
import java.util.*


object UtilsImages {


    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val temp: String = Base64.getEncoder().encodeToString(b)
        return if (temp == null) {
            null
        } else temp
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.getDecoder().decode(encodedString)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            bitmap
        } catch (e: Exception) {
            e.message
            null
        }
    }


    }

