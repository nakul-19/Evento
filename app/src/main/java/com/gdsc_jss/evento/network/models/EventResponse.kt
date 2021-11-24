package com.gdsc_jss.evento.network.models

data class EventResponse(
    val _id: String,
    val contactDetails: ContactDetails,
    val cta: String,
    val dateAndTime: String,
    val description: String,
    val image: String,
    val location: String,
    val name: String,
    val society: Society,
    val speakers: List<Any>,
    val updatedAt: String
)

data class ContactDetails(
    val name: String,
    val number: String
)

data class Society(
    val _id: String,
    val handle: String,
    val image: String,
    val name: String
)