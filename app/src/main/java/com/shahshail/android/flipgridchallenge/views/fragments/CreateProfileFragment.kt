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
package com.shahshail.android.flipgridchallenge.views.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shahshail.android.flipgridchallenge.R
import com.shahshail.android.flipgridchallenge.models.UserProfileDto
import com.shahshail.android.flipgridchallenge.utils.CreateProfileValidator
import com.shahshail.android.flipgridchallenge.utils.clearError
import com.shahshail.android.flipgridchallenge.utils.setAvatarRoundedBitmap
import com.shahshail.android.flipgridchallenge.utils.setErrorWithSeparator
import com.shahshail.android.flipgridchallenge.views.viewmodels.CreateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    //region Nested Classes

    //endregion

    //region Statics

    // endregion

    // region member variables
    val viewModel by viewModels<CreateProfileViewModel>()

    @Inject
    lateinit var createProfileValidator: CreateProfileValidator

    private lateinit var profileHeaderTextView: TextView
    private lateinit var profileDescriptionTextView: TextView
    private lateinit var addAvatarImageButton: ImageButton
    private lateinit var addAvatarTextView: TextView
    private lateinit var firstNameTextInputLayout: TextInputLayout
    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var emailAddressTextInputLayout: TextInputLayout
    private lateinit var emailAddressEditText: TextInputEditText
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var webUrlTextInputLayout: TextInputLayout
    private lateinit var webUrlEditText: TextInputEditText
    private lateinit var submitButton: AppCompatButton
    private val imageCaptureRequestCode = 11

    // endregion

    // region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == imageCaptureRequestCode && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            addAvatarTextView.visibility = View.GONE
            addAvatarImageButton.setAvatarRoundedBitmap(imageBitmap)
        }
    }

    //endregion

    // region private functions

    private fun initViews(view: View) {
        setupTextViews(view)
        setupFirstName(view)
        setupPassword(view)
        setupEmailAddress(view)
        setupWebUrl(view)
        setupAvatarView(view)
        setupSubmitButton(view)
    }

    private fun setupTextViews(view: View) = with(view) {
        profileHeaderTextView = view.findViewById(R.id.header)
        profileHeaderTextView.text = getString(R.string.profile_creation_header)
        profileDescriptionTextView = view.findViewById(R.id.description)
        profileDescriptionTextView.text = getString(R.string.profile_creation_instruction)
    }

    private fun setupFirstName(view: View) = with(view) {
        firstNameTextInputLayout = findViewById(R.id.first_name_text_input_layout)
        firstNameEditText = findViewById(R.id.firstName_edit_text)
        firstNameTextInputLayout.hint = getString(R.string.first_name)
        firstNameEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                firstNameTextInputLayout.clearError()
            }
        }
    }

    private fun setupEmailAddress(view: View) = with(view) {
        emailAddressTextInputLayout = findViewById(R.id.email_text_input_layout)
        emailAddressEditText = findViewById(R.id.email_edit_text)
        emailAddressTextInputLayout.hint = getString(R.string.email_address)
        firstNameEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                firstNameTextInputLayout.clearError()
            }
        }
    }

    private fun setupPassword(view: View) = with(view) {
        passwordTextInputLayout = findViewById(R.id.password_text_input_layout)
        passwordEditText = findViewById(R.id.password_edit_text)
        passwordTextInputLayout.hint = getString(R.string.password)
        firstNameEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                firstNameTextInputLayout.clearError()
            }
        }
    }

    private fun setupWebUrl(view: View) = with(view) {
        webUrlTextInputLayout = findViewById(R.id.website_text_input_layout)
        webUrlEditText = findViewById(R.id.website_edit_text)
        webUrlTextInputLayout.hint = getString(R.string.website)
        firstNameEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                firstNameTextInputLayout.clearError()
            }
        }
    }

    private fun setupAvatarView(view: View) = with(view) {
        addAvatarImageButton = findViewById(R.id.profile_image_button)
        addAvatarTextView = findViewById(R.id.add_avatar_text_view)
        addAvatarImageButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun setupSubmitButton(view: View) = with(view) {
        submitButton = findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            val validations = listOf(
                validateFirstName(),
                validateEmailAddress(),
                validatePassword(),
                validateWebsite()
            )
            if (!validations.any { false }) {
                val firstName = firstNameEditText.text?.toString()
                val email = emailAddressEditText.text.toString()
                val password = passwordEditText.text.toString()
                val website = webUrlEditText.text?.toString()
                val userProfile = UserProfileDto(firstName, email, password, website, null)
            }
        }
    }

    private fun validateFirstName(): Boolean {
        val text = firstNameEditText.text?.toString()
        val flags = createProfileValidator.validateFirstName(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(firstNameTextInputLayout, flags)
        return false
    }

    private fun validateEmailAddress(): Boolean {
        val text = emailAddressEditText.text?.toString()
        val flags = createProfileValidator.validateEmailAddress(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(emailAddressTextInputLayout, flags)
        return false
    }

    private fun validatePassword(): Boolean {
        val text = passwordEditText.text?.toString()
        val flags = createProfileValidator.validatePassword(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(passwordTextInputLayout, flags)
        return false
    }

    private fun validateWebsite(): Boolean {
        val text = webUrlEditText.text?.toString()
        val flags = createProfileValidator.validateWebsite(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(webUrlTextInputLayout, flags)
        return false
    }

    private fun handleValidationFailed(
        layout: TextInputLayout,
        flags: List<CreateProfileValidator.CreateProfileErrorFlag>
    ) {
        val errorList = flags.map { getString(it.resId) }
        layout.setErrorWithSeparator(errorList)
    }

    private fun takePhoto() {
        // If we are using the camera by invoking an existing camera app, then we dont need to request permission.
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, imageCaptureRequestCode)
        } catch (e: ActivityNotFoundException) {

        }
    }

    // endregion

}