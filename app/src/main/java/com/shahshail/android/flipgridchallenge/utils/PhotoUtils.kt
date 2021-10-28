package com.shahshail.android.flipgridchallenge.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import com.shahshail.android.flipgridchallenge.R
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import java.io.InputStream
import javax.inject.Inject

/*
 * Copyright 2021 shailshah
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * created by shailshah, 10/27/21
 */

@ActivityScoped
class PhotoUtils @Inject constructor(val permissionsUtil: PermissionsUtil, @ApplicationContext val context: Context) {

    companion object {
        private const val TAG = "PhotoUtils"
    }

    fun getPhotoSelectIntent(fragment: Fragment): Intent? {
        val context = fragment.context ?: return null
        if (!permissionsUtil.isReadStoragePermissionGranted(context)) {
            permissionsUtil.requestStoragePermission(fragment)
            return null
        }
        return constructPhotoSelectIntent(context)
    }

    private fun constructPhotoSelectIntent(context: Context): Intent {
        val chooseImageType = "image/*"
        val getContentIntent = Intent(Intent.ACTION_GET_CONTENT)
        getContentIntent.type = chooseImageType

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = chooseImageType
        }

        val intent = Intent.createChooser(getContentIntent, context.getString(R.string.select_image)).apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        }
        return intent
    }

    fun getBitmapFromUri(uri: Uri): Bitmap? {
        val inputStream: InputStream?
        try {
            inputStream = context.contentResolver?.openInputStream(uri) ?: return null
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e(TAG, "getBitmapFromUri: ", e)
        }
        return null
    }
}