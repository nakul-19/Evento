package com.gdsc_jss.evento.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc_jss.evento.databinding.FragmentUpcomingEventsBinding
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.EventResponse
import com.gdsc_jss.evento.util.handleErrorsWithSnackbar
import com.gdsc_jss.evento.viewmodels.UpcomingEventsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingEventsFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingEventsBinding
    private val viewModel: UpcomingEventsViewModel by activityViewModels()
    private val listOfEvents = ArrayList<EventResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingEventsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setEvents()
    }

    private fun setView() {
        binding.upcomingEventsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EventsAdapter(listOfEvents)
        }
        binding.upcomingEventsRefresh.setOnRefreshListener {
            viewModel.refreshEvents()
        }
    }

    private fun setEvents() {
        viewModel.events.observe(viewLifecycleOwner){
            when(it) {
                is Resource.Success -> {
                    binding.upcomingEventsRefresh.isRefreshing=false
                    listOfEvents.clear()
                    listOfEvents.addAll(it.r)
                    binding.upcomingEventsList.adapter?.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    binding.upcomingEventsRefresh.isRefreshing=false
                    Snackbar.make(binding.root,it.msg,Snackbar.LENGTH_SHORT).setAction("Retry"){
                        viewModel.refreshEvents()
                    }
                }
                else -> {
                    binding.upcomingEventsRefresh.isRefreshing=true
                }
            }
        }
        viewModel.getEvents()
    }
}