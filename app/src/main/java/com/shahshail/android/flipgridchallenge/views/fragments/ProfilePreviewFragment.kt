package com.shahshail.android.flipgridchallenge.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shahshail.android.flipgridchallenge.R

class ProfilePreviewFragment : Fragment() {

    //region Nested Classes

    //endregion

    //region Statics

    // endregion

    // region member variables

    // endregion

    // region lifecycle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_preview, container, false)
    }

    //endregion

    // region private functions

    // endregion
}