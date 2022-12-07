package com.hudhudit.catchapp.ui.main.catcher.history

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.catchee.history.CatcheeOrderHistory
import com.hudhudit.catchapp.apputils.modules.catcher.history.CatcherOrderHistory
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentCatcherHistoryBinding
import com.hudhudit.catchapp.ui.main.MainActivity
import com.hudhudit.catchapp.ui.main.catchee.history.CatcheeHistoryAdapter
import com.hudhudit.catchapp.utils.AppConstants
import com.hudhudit.catchapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatcherHistoryFragment : BaseFragment() {

    lateinit var binding: FragmentCatcherHistoryBinding
    lateinit var mainActivity: MainActivity
    private val viewModel by viewModels<CatcherHistoryViewModel>()
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatcherHistoryBinding.inflate(layoutInflater)
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
        getHistoryData()
        getOrdersHistory()
        binding.hiUser.text = resources.getString(R.string.hi)+ " "+ AppConstants.catcherUser.results.name

    }

    private fun onClick(){
        binding.navigateBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun getHistoryData(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getHistoryData(AppConstants.catcherUser.token)
        viewModel.historyDataStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.totalFees.text = it.data!!.results.total_price
                    binding.totalCatches.text = it.data!!.results.no_orders
                }
                Resource.Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun getOrdersHistory(){
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getOrderHistory(AppConstants.catcherUser.token, page.toString())
        viewModel.ordersHistoryStatus.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    setHistoryAdapter(it.data!!.results)
                }
                Resource.Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setHistoryAdapter(orders: MutableList<CatcherOrderHistory>){
        val adapter = CatcherHistoryAdapter(this, orders)
        binding.historyRV.adapter = adapter
        binding.historyRV.layoutManager = LinearLayoutManager(mainActivity)

        if (orders.size == 0){
            binding.noHistory.visibility = View.VISIBLE
            binding.historyNSV.visibility = View.GONE
        }else{
            binding.noHistory.visibility = View.GONE
            binding.historyNSV.visibility = View.VISIBLE
        }
    }
}