package com.hudhudit.catchapp.ui.registration.usertypes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.databinding.FragmentUserTypesBinding
import com.hudhudit.catchapp.ui.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserTypesFragment : Fragment() {

    lateinit var binding: FragmentUserTypesBinding
    lateinit var registrationActivity: RegistrationActivity
    var userType = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserTypesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RegistrationActivity) {
            registrationActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick(){
        binding.catcher.setOnClickListener {
            binding.catcherSelectedLayout.visibility = View.VISIBLE
            binding.catcheeSelectedLayout.visibility = View.GONE
            userType = "0"
        }
        binding.catchee.setOnClickListener {
            binding.catcherSelectedLayout.visibility = View.GONE
            binding.catcheeSelectedLayout.visibility = View.VISIBLE
            userType = "1"
        }
        binding.continueBtn.setOnClickListener {
            if (userType == "1"){
                findNavController().navigate(R.id.action_userTypesFragment_to_catcheeRegistrationFragment)
            }
        }
    }

}