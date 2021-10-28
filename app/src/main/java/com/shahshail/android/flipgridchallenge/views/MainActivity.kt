package com.shahshail.android.flipgridchallenge.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shahshail.android.flipgridchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // endregion
}