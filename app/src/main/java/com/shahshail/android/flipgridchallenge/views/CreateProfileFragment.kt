package com.shahshail.android.flipgridchallenge.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.shahshail.android.flipgridchallenge.R
import com.shahshail.android.flipgridchallenge.viewmodels.CreateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    //region Nested Classes

    //endregion

    //region Statics

    // endregion

    // region member variables
    val viewModel by viewModels<CreateProfileViewModel>()

    // endregion

    // region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_profile, container, false)
    }

    //endregion

}