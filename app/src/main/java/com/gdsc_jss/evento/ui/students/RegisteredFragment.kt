package com.gdsc_jss.evento.ui.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc_jss.evento.databinding.FragmentRegisteredBinding
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.EventResponse
import com.gdsc_jss.evento.ui.common.EventsAdapter
import com.gdsc_jss.evento.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
class RegisteredFragment : Fragment() {

    private lateinit var binding: FragmentRegisteredBinding
    private val listOfEvents = arrayListOf<EventResponse>()
    private val viewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisteredBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setEvents()

    }

    @DelicateCoroutinesApi
    private fun setView() {
        binding.registeredEventsRefresh.isRefreshing=true
        binding.registeredEventsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EventsAdapter(listOfEvents)
        }
        binding.registeredEventsRefresh.setOnRefreshListener {
            viewModel.getUser()
        }
    }

    @DelicateCoroutinesApi
    private fun setEvents() {
        viewModel.userEvents.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.registeredEventsRefresh.isRefreshing = false
                    listOfEvents.clear()
                    listOfEvents.addAll(it.r)
                    binding.registeredEventsList.adapter?.notifyDataSetChanged()
                    binding.registeredEventsList.visibility = View.GONE
                    binding.illustration.visibility = if (listOfEvents.isEmpty()) {
                        binding.registeredEventsList.visibility = View.GONE
                        View.VISIBLE
                    } else {
                        binding.registeredEventsList.visibility = View.VISIBLE
                        View.GONE
                    }
                }
                is Resource.Error -> {
                    binding.registeredEventsRefresh.isRefreshing = false
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).setAction("Retry") {
                        viewModel.getUser()
                    }
                }
                else -> {
                    binding.registeredEventsRefresh.isRefreshing = true
                    binding.illustration.visibility = View.GONE
                }
            }
        }
        UserViewModel.user.observe(viewLifecycleOwner) {
            if (it is AuthResource.Authenticated)
                viewModel.getUserEvents()
        }
    }

}