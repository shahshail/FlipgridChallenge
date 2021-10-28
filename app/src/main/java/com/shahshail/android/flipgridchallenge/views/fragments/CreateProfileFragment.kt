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
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shahshail.android.flipgridchallenge.R
import com.shahshail.android.flipgridchallenge.models.UserProfileDto
import com.shahshail.android.flipgridchallenge.utils.*
import com.shahshail.android.flipgridchallenge.views.viewmodels.CreateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    //region Nested Classes

    //endregion

    //region Statics

    companion object {
        private const val TAG = "CreateProfileFragment"
        private const val IMAGE_DATA = "data"
    }
    
    // endregion

    // region member variables
    val viewModel by viewModels<CreateProfileViewModel>()
    private val imageCaptureRequestCode = 11
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
    private var cameraAppNotInstalledDialog: AlertDialog? = null
    private var userProfileBitmap: Bitmap? = null

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
            val imageBitmap = data?.extras?.get(IMAGE_DATA) as? Bitmap
            imageBitmap?.let { bitmap ->
                userProfileBitmap = bitmap
                addAvatarTextView.visibility = View.GONE
                addAvatarImageButton.setAvatarRoundedBitmap(bitmap)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        cameraAppNotInstalledDialog?.dismiss()
        userProfileBitmap?.let { bitmap ->
            viewModel.saveUserPicture(bitmap)
        }
    }

    //endregion

    // region private functions

    private fun initViews(view: View) {
        Log.d(TAG, "initViews: ")
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
        firstNameTextInputLayout = findViewById(R.id.create_profile_first_name_text_input_layout)
        firstNameEditText = findViewById(R.id.create_profile_firstName_edit_text)
        firstNameEditText.maxLines = Constants.MAX_FIRST_NAME_LENGTH
        firstNameTextInputLayout.hint = getString(R.string.first_name)
        firstNameEditText.afterTextChanged {
            firstNameTextInputLayout.clearError()
        }
    }

    private fun setupEmailAddress(view: View) = with(view) {
        emailAddressTextInputLayout = findViewById(R.id.create_profile_email_text_input_layout)
        emailAddressEditText = findViewById(R.id.create_profile_email_edit_text)
        emailAddressEditText.maxLines = Constants.MAX_EMAIL_ADDRESS_LENGTH
        emailAddressTextInputLayout.hint = getString(R.string.email_address)
        emailAddressEditText.afterTextChanged {
            emailAddressTextInputLayout.clearError()
        }
    }

    private fun setupPassword(view: View) = with(view) {
        passwordTextInputLayout = findViewById(R.id.create_profile_password_text_input_layout)
        passwordEditText = findViewById(R.id.create_profile_password_edit_text)
        passwordEditText.maxLines = Constants.MAX_PASSWORD_LENGTH
        passwordTextInputLayout.hint = getString(R.string.password)
        passwordEditText.afterTextChanged {
            passwordTextInputLayout.clearError()
        }
    }

    private fun setupWebUrl(view: View) = with(view) {
        webUrlTextInputLayout = findViewById(R.id.create_profile_website_text_input_layout)
        webUrlEditText = findViewById(R.id.create_profile_website_edit_text)
        webUrlEditText.maxLines = Constants.MAX_WEB_URL_LINK_LENGTH
        webUrlTextInputLayout.hint = getString(R.string.website)
        webUrlEditText.afterTextChanged {
            webUrlTextInputLayout.clearError()
        }
    }

    private fun setupAvatarView(view: View) = with(view) {
        addAvatarImageButton = findViewById(R.id.profile_image_button)
        addAvatarTextView = findViewById(R.id.add_avatar_text_view)
        val cachedPicture = viewModel.getUserProfilePicture()
        cachedPicture?.let { bitmap ->
            userProfileBitmap = bitmap
            addAvatarTextView.visibility = View.GONE
            addAvatarImageButton.setAvatarRoundedBitmap(bitmap)
        }
        addAvatarImageButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun setupSubmitButton(view: View) = with(view) {
        submitButton = findViewById(R.id.create_profile_submit_button)
        submitButton.setOnClickListener {
            // validate all fields
            val validations = listOf(
                validateFirstName(),
                validateEmailAddress(),
                validatePassword(),
                validateWebsite()
            )

            val isValidationPassed = !validations.contains(false)

            // if validation successful then navigate to profile preview
            if (isValidationPassed) {
                val firstName = firstNameEditText.text?.toString()?.trim()
                val email = emailAddressEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val website = webUrlEditText.text?.toString()?.trim()
                val userProfile = UserProfileDto(firstName, email, password, website, userProfileBitmap)
                val action = CreateProfileFragmentDirections.actionCreateProfileToProfilePreview(userProfile)
                findNavController().navigate(action)
            } else {
                Log.w(TAG, "setupSubmitButton: validations failed")
            }
        }
    }

    private fun validateFirstName(): Boolean {
        val text = firstNameEditText.text?.toString()?.trim()
        val flags = createProfileValidator.validateFirstName(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(firstNameTextInputLayout, flags)
        return false
    }

    private fun validateEmailAddress(): Boolean {
        val text = emailAddressEditText.text?.toString()?.trim()
        val flags = createProfileValidator.validateEmailAddress(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(emailAddressTextInputLayout, flags)
        return false
    }

    private fun validatePassword(): Boolean {
        val text = passwordEditText.text?.toString()?.trim()
        val flags = createProfileValidator.validatePassword(text)
        if (flags.isEmpty()) {
            return true
        }
        handleValidationFailed(passwordTextInputLayout, flags)
        return false
    }

    private fun validateWebsite(): Boolean {
        val text = webUrlEditText.text?.toString()?.trim()
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
            // if camera app is not installed then ask the user to install it from playstore
            Log.w(TAG, "takePhoto: camera app is not found...")
            showInstallCameraAppDialog()
        }
    }

    private fun showInstallCameraAppDialog() {
        if (cameraAppNotInstalledDialog?.isShowing == true) {
            cameraAppNotInstalledDialog?.dismiss()
        }

        cameraAppNotInstalledDialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.app_is_not_installed)
                setMessage(R.string.install_camera_app_from_plastore)
                setPositiveButton(R.string.store) { dialog, id ->
                    launchPlayStore()
                }
                setNegativeButton(R.string.cancel) { dialog, id ->
                    dialog.dismiss()
                }
            }
            builder.create()
        }

        cameraAppNotInstalledDialog?.show()
    }

    private fun launchPlayStore() {
        try {
            // try to navigate to camera app
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${Constants.GOOGLE_CAMERA_APP_ID}")))
        } catch (e: ActivityNotFoundException) {
            // if the app is not found then simply launch playstore
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com")))
        }
    }

    // endregion
}