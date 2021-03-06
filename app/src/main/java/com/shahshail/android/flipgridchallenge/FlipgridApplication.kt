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
package com.shahshail.android.flipgridchallenge

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlipgridApplication: Application() {

    //region Nested Classes

    //endregion

    //region Statics
    
    companion object {
        private const val TAG = "FlipgridApplication"
    }
    
    //endregion

    //region Lifecycle

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }
    
    //endregion
    
    
    

    
}