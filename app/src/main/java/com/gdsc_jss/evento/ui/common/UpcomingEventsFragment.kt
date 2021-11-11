package com.gdsc_jss.evento.ui.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.gdsc_jss.evento.databinding.FragmentUpcomingEventsBinding
import com.gdsc_jss.evento.viewmodels.UpcomingEventsViewModel

class UpcomingEventsFragment : Fragment() {

    private lateinit var b: FragmentUpcomingEventsBinding
    private val viewModel: UpcomingEventsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b= FragmentUpcomingEventsBinding.inflate(layoutInflater,container,false)
        return b.root
    }
}