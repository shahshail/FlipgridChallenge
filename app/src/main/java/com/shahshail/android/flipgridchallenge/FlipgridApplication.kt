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