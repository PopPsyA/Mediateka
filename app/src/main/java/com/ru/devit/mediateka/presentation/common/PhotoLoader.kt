package com.ru.devit.mediateka.presentation.common

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.ru.devit.mediateka.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

class PhotoLoader(private val context: Context , private val pictureName: String): Target {

    companion object {
        private const val ALBUM_NAME = "Mediateka"
    }

    private val executorService = Executors.newSingleThreadExecutor()

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        executorService.submit{
            val albumDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) , ALBUM_NAME)
            if (!albumDir.exists()){
                albumDir.mkdir()
            }
            val pathToPicture = albumDir.path.plus("/").plus(pictureName)
            val pictureFile = File(pathToPicture)
            if (!pictureFile.exists()){
                pictureFile.createNewFile()
                val fileOutputStream = FileOutputStream(pictureFile)
                fileOutputStream.use {
                    bitmap?.compress(Bitmap.CompressFormat.JPEG , 80 , fileOutputStream)
                    fileOutputStream.flush()
                }
                sendPictureToGallery(pathToPicture)
            }
            Toast.makeText(context , context.getString(R.string.message_picture_download_successully) , Toast.LENGTH_LONG).show()
        }
    }

    private fun sendPictureToGallery(pathToPicture: String){
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val pictureFile = File(pathToPicture)
        val pictureUri = Uri.fromFile(pictureFile)
        intent.data = pictureUri
        context.sendBroadcast(intent)
    }
}