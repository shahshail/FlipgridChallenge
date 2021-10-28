package com.shahshail.android.flipgridchallenge.views.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.shahshail.android.flipgridchallenge.R
import com.shahshail.android.flipgridchallenge.models.UserProfileDto
import com.shahshail.android.flipgridchallenge.utils.makeItLink
import com.shahshail.android.flipgridchallenge.utils.setAvatarRoundedBitmap

class ProfilePreviewFragment : Fragment() {

    //region Nested Classes

    //endregion

    //region Statics

    companion object {
        private const val TAG = "ProfilePreviewFragment"
    }

    // endregion

    // region member variables

    private lateinit var previewHeaderTextView: TextView
    private lateinit var previewDescriptionTextView: TextView
    private lateinit var previewAddTextView: TextView
    private lateinit var previewProfileImageButton: ImageButton
    private lateinit var userProfileDto: UserProfileDto
    private lateinit var websiteTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var nameTextView: TextView

    // endregion

    // region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileDto = arguments?.getParcelable("userProfileDto")
            ?: throw IllegalStateException("user profile data is missing")
        initViews(view)
    }

    //endregion

    // region private functions

    private fun initViews(view: View) = with(view) {
        setupUserData(view)
        setupConfirmationText(view)
    }

    private fun setupUserData(view: View) = with(view) {
        websiteTextView = findViewById(R.id.website_text_view)
        emailTextView = findViewById(R.id.profile_preview_email_text_view)
        nameTextView = findViewById(R.id.profile_preview_first_name_text_view)

        // website
        userProfileDto.website?.let { url ->
            websiteTextView.visibility = View.VISIBLE
            websiteTextView.text = url
            websiteTextView.makeItLink()
            websiteTextView.setOnClickListener {
                launchUserWebsite(url)
            }
        } ?: run {
            websiteTextView.visibility = View.GONE
        }

        // first name
        userProfileDto.firstName?.let { firstName ->
            nameTextView.visibility = View.VISIBLE
            nameTextView.text = firstName
        } ?: run {
            nameTextView.visibility = View.GONE
        }

        // email
        emailTextView.visibility = View.VISIBLE
        emailTextView.text = userProfileDto.emailAddress


    }

    private fun setupConfirmationText(view: View) = with(view) {
        previewHeaderTextView = findViewById(R.id.header)
        previewDescriptionTextView = findViewById(R.id.description)
        previewAddTextView = findViewById(R.id.add_avatar_text_view)
        previewProfileImageButton = findViewById(R.id.profile_image_button)

        val name = userProfileDto.firstName ?: getString(R.string.temp_profile_name_placeholder)
        previewHeaderTextView.text = getString(R.string.profile_greet_header, name)
        previewDescriptionTextView.text = getString(R.string.profile_creation_instruction)

        previewAddTextView.visibility = View.GONE

        val avatar = userProfileDto.profilePicture
        if (avatar == null) {
            // fallback to default
            previewProfileImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_foreground, null))
        } else {
            previewProfileImageButton.setAvatarRoundedBitmap(avatar)
        }
    }

    private fun launchUserWebsite(url: String) {
        val websiteUri = Uri.parse(addProtocolPrefix(url))
        val implicitIntent = Intent(ACTION_VIEW).apply {
            data = websiteUri
        }
        try {
            startActivity(implicitIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "launchUserWebsite: ", e)
        }
    }

    /**
     * if the user does not have url protocol prefix
     * file:// or https:// are valid so safe to user regex
     */
    private fun addProtocolPrefix(string: String?) : String? {
        if (string == null) {
            return null
        }
        if (!string.lowercase().matches(Regex("^\\w+://.*"))) {
            return "http://$string";
        }
        return string
    }

    // endregion
}