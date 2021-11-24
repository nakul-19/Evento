package com.gdsc_jss.evento.ui.students

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gdsc_jss.evento.databinding.ActivityEventBinding
import com.gdsc_jss.evento.network.AuthResource
import com.gdsc_jss.evento.network.Resource
import com.gdsc_jss.evento.network.models.EventResponse
import com.gdsc_jss.evento.util.formatTo
import com.gdsc_jss.evento.util.handleErrorsWithSnackbar
import com.gdsc_jss.evento.util.toDate
import com.gdsc_jss.evento.viewmodels.EventViewModel
import com.gdsc_jss.evento.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    companion object {
        private var event: EventResponse? = null
        fun start(context: Context, _event: EventResponse) {
            event = _event
            context.startActivity(Intent(context, EventActivity::class.java))
        }
    }

    private lateinit var binding: ActivityEventBinding
    private val viewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setClick()
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        binding.apply {
            name.text = event?.name
            description.text = event?.description
            societyName.text = event?.society?.name
            Glide.with(root).load(event?.image).into(banner)
            Glide.with(root).load(event?.society?.image).into(societyDp)
            date.text = event?.dateAndTime?.toDate()?.formatTo("dd MMMM', 'YYYY")
            time.text = event?.dateAndTime?.toDate()?.formatTo("hh:mm a")
            location.text = event?.location?:""
            contact.text = "Contact: "+event?.contactDetails?.name+" - "+ event?.contactDetails?.number
        }

        if (UserViewModel.user.value is AuthResource.Authenticated) {
            if ((UserViewModel.user.value as AuthResource.Authenticated)
                    .user?.registeredIn?.contains(event?._id))
                        binding.registerButton.text = "REGISTERED"
        }

        if (event?.cta?.isBlank()==false) {
            binding.registerButton.text="Click to open"
        }

    }

    private fun setClick() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.registerButton.setOnClickListener { v ->
            if ((v as Button).text == "REGISTERED")
                return@setOnClickListener

            if (event?.cta?.isBlank()==false) {
                try {
                    val uri = Uri.parse(event?.cta?:"")
                    this.startActivity(Intent(Intent.ACTION_VIEW,uri))
                    return@setOnClickListener
                } catch (e:Exception) {

                }
            }

            viewModel.registerForEvent(event?._id!!).observe(this) {
                when (it) {
                    is Resource.Success -> {
                        v.text = "REGISTERED"
                    }
                    is Resource.Loading -> {
                        v.text = "Registering.."
                    }
                    is Resource.Error -> {
                        v.text = "Register Here"
                        handleErrorsWithSnackbar(binding.root, it.msg)
                    }
                }
            }
        }
    }
}