/*
 * Copyright 2021 Open source license
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
package com.shahshail.android.flipgridchallenge.views.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for create profile fragment
 * using using saved state handle saving user data in configuration change for better performance
 */
@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val EXTRA_PROFILE_IMAGE = "extraProfileImage"
    }

    /**
     * save user profile picture to survive configuration change
     */
    fun saveUserPicture(profileBitmap: Bitmap) = savedStateHandle.set(EXTRA_PROFILE_IMAGE, profileBitmap)

    /**
     * retrieves user profile picture from saved state
     */
    fun getUserProfilePicture(): Bitmap? = savedStateHandle.get(EXTRA_PROFILE_IMAGE)
}