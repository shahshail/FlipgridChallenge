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
package com.shahshail.android.flipgridchallenge.utils

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shahshail.android.flipgridchallenge.R

/**
 * helper view extensions
 */

/**
 * set multiline error support for TextInputLayout
 * by default it only supports single line
 */
fun TextInputLayout.setMultiLineErrorSupport() {
    isErrorEnabled = true
    val errorTextView = findViewById<TextView>(com.google.android.material.R.id.textinput_error)
    errorTextView.maxLines = Constants.MAX_ERROR_LINES
}

/**
 * set list of errors with line saperators
 */
fun TextInputLayout.setErrorWithSeparator(errorList: List<String>) {
    if (errorList.isEmpty()) return
    if (errorList.size > Constants.MAX_ERROR_LINES) {
        throw IllegalStateException("max error lines reached")
    }

    setMultiLineErrorSupport()

    val stringBuilder = StringBuilder(errorList[0])
    for (errorIndex in 1 until errorList.size) {
        stringBuilder.append(System.getProperty("line.separator"))
        stringBuilder.append(errorList[errorIndex])
    }
    error = stringBuilder.toString()
}

fun TextInputEditText.afterTextChanged(action: (editable: Editable?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) = action.invoke(s)
    })
}

fun TextInputLayout.clearError() {
    if (!isErrorEnabled) return
    error = null
    isErrorEnabled = false
}

fun ImageButton.setAvatarRoundedBitmap(bitmap: Bitmap) {
    val dr: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
    dr.cornerRadius = resources.getDimension(R.dimen.default_corner_radius)
    setImageDrawable(dr)
}