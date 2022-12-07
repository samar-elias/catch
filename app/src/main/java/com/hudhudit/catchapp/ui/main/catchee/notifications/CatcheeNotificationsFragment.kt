package com.hudhudit.catchapp.ui.main.catchee.notifications

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.catchee.notifications.CatcheeNotification
import com.hudhudit.catchapp.apputils.modules.registration.CheckPhone
import com.hudhudit.catchapp.apputils.modules.registration.UserSignIn
import com.hudhudit.catchapp.apputils.modules.registration.catcheeregistration.CatcheeUserSignUp
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcheeNotificationsBinding
import com.hudhudit.catchapp.ui.main.MainActivity
import com.hudhudit.catchapp.ui.registration.catchee.register.CatcheeRegistrationFragmentDirections
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatcheeNotificationsFragment : BaseFragment() {

    lateinit var binding: FragmentCatcheeNotificationsBinding
    lateinit var mainActivity: MainActivity
    private val viewModel by viewModels<CatcheeNotificationsViewModel>()
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatcheeNotificationsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
        getNotifications()

    }

    private fun onClick(){
        binding.navigateBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun getNotifications(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getNotifications(AppConstants.catcheeUser.token, page.toString())
        viewModel.notificationsStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if(it.data!!.results == null){
                        setNotificationsAdapter(it.data.results!!.notifications)
                        binding.noNotifications.visibility = View.GONE
                        binding.notificationsNSV.visibility = View.VISIBLE
                    }else{
                        binding.noNotifications.visibility = View.VISIBLE
                        binding.notificationsNSV.visibility = View.GONE
                    }
                }
                Resource.Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                    binding.noNotifications.visibility = View.VISIBLE
                    binding.notificationsNSV.visibility = View.GONE
                }
            }
        }
    }

    private fun setNotificationsAdapter(notifications: MutableList<CatcheeNotification>){
        val adapter = CatcheeNotificationsAdapter(this, notifications)
        binding.notificationsRV.adapter = adapter
        binding.notificationsRV.layoutManager = LinearLayoutManager(mainActivity)

        if (notifications.size == 0){
            binding.noNotifications.visibility = View.VISIBLE
            binding.notificationsNSV.visibility = View.GONE
        }else{
            binding.noNotifications.visibility = View.GONE
            binding.notificationsNSV.visibility = View.VISIBLE
        }
    }

}