package com.gdsc_jss.evento.ui.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc_jss.evento.databinding.FragmentRegisteredBinding
import com.gdsc_jss.evento.network.models.EventResponse
import com.gdsc_jss.evento.ui.common.EventsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisteredFragment : Fragment() {

    private lateinit var binding: FragmentRegisteredBinding
    private val listOfEvents = arrayListOf<EventResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisteredBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()

    }

    private fun setRecycler() {
        binding.registeredEventsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EventsAdapter(listOfEvents)
        }
    }
}