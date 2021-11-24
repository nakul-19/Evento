package com.gdsc_jss.evento.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc_jss.evento.R
import com.gdsc_jss.evento.databinding.FragmentUpcomingEventsBinding
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.EventResponse
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setEvents() {
        viewModel.events.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.upcomingEventsRefresh.isRefreshing = false
                    listOfEvents.clear()
                    listOfEvents.addAll(it.r)
                    binding.upcomingEventsList.adapter?.notifyDataSetChanged()
                    if (listOfEvents.isEmpty()) {
                        binding.upcomingEventsList.visibility = View.GONE
                        binding.illustration.apply {
                            setImageDrawable(context.getDrawable(R.drawable.no_data))
                            visibility = View.VISIBLE
                        }
                    } else {
                        binding.upcomingEventsList.visibility = View.VISIBLE
                        binding.illustration.apply {
                            visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    binding.upcomingEventsRefresh.isRefreshing = false
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).setAction("Retry") {
                        viewModel.refreshEvents()
                    }.show()
                    binding.upcomingEventsList.visibility = View.GONE
                    binding.illustration.apply {
                        setImageDrawable(context.getDrawable(R.drawable.network_error))
                        visibility = View.VISIBLE
                    }
                }
                else -> {
                    binding.upcomingEventsRefresh.isRefreshing = true
                }
            }
        }
        viewModel.getEvents()
    }
}