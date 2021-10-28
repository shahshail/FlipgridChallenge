package com.shahshail.android.flipgridchallenge.utils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.scopes.ActivityScoped
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

/**
 * util class to handle permission requests
 */
@ActivityScoped
class PermissionsUtil @Inject constructor() {

    companion object {
        const val READ_STORAGE_PERMISSION_REQUEST_CODE = 101
    }

    fun isReadStoragePermissionGranted(context: Context): Boolean {
        return PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE)
    }

    fun requestStoragePermission(fragment: Fragment) {
        fragment.requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION_REQUEST_CODE)
    }

}