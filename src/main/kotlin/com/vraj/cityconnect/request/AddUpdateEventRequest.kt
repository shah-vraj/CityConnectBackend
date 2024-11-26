package com.vraj.cityconnect.request

import com.vraj.cityconnect.enums.Country
import com.vraj.cityconnect.model.Event
import java.time.LocalTime

data class AddUpdateEventRequest(
    val id: Long = 0,
    val title: String,
    val description: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val country: Country
) {

    fun toEvent() = Event(
        id = id,
        title = title,
        description = description,
        startTime = startTime,
        endTime = endTime,
        country = country
    )
}
