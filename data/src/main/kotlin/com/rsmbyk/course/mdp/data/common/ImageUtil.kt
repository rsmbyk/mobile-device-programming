package com.rsmbyk.course.mdp.data.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

private val imageFileExtensions = arrayOf ("jpg", "jpeg", "png", "gif")
private const val BASE64_PREFIX = "data:image/jpeg;base64,"

private fun String.endsWithIgnoreCase (suffix: String): Boolean =
    endsWith (suffix, true)

fun File.isImageFile (): Boolean =
    imageFileExtensions.any (name::endsWithIgnoreCase)

fun ByteArray.compress (): ByteArrayOutputStream {
    val image: Bitmap = BitmapFactory.decodeByteArray (this, 0, size)
    val scaledImage: Bitmap = Bitmap.createScaledBitmap (image, 96, 96, true)
    val bos = ByteArrayOutputStream ()
    scaledImage.compress (Bitmap.CompressFormat.JPEG, 100, bos)
    return bos
}

fun ByteArrayOutputStream.toBase64ImageString (): String =
    BASE64_PREFIX + toBase64ImageStringWithoutPrefix ()

fun ByteArrayOutputStream.toBase64ImageStringWithoutPrefix  (): String =
    Base64.encodeToString (toByteArray (), Base64.DEFAULT)
