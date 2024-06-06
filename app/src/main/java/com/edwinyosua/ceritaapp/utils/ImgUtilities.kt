package com.edwinyosua.ceritaapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private const val MAX_SIZE = 1000000
private const val FILE_NAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILE_NAME_FORMAT, Locale.US).format(Date())

fun uriToFile(uri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(uri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0)
        outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun createCustomTempFile(context: Context): File =
    File.createTempFile(timeStamp, ".jpg", context.externalCacheDir)


fun File.reduceFileImg(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAX_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun Bitmap.getRotatedBitmap(file: File): Bitmap {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImg(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImg(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImg(this, 270F)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}


fun rotateImg(bitMapFile: Bitmap, angle: Float): Bitmap {

    val matrix = Matrix()
    matrix.postRotate(angle)

    return Bitmap.createBitmap(
        bitMapFile,
        0, 0,
        bitMapFile.width,
        bitMapFile.height,
        matrix,
        true
    )
}

