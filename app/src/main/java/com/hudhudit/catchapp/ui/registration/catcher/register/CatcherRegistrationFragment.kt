package com.hudhudit.catchapp.ui.registration.catcher.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hudhudit.catchapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatcherRegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catcher_registration, container, false)
    }

}