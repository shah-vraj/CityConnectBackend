package com.vraj.cityconnect.request

import com.vraj.cityconnect.enums.Country
import com.vraj.cityconnect.model.Event
import org.springframework.web.multipart.MultipartFile
import java.time.LocalTime

data class AddUpdateEventRequest(
    val id: Long = 0,
    val title: String,
    val description: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val country: Country,
    val image: MultipartFile? = null
) {

    fun toEvent(imageUrl: String? = null) = Event(
        id = id,
        title = title,
        description = description,
        startTime = startTime,
        endTime = endTime,
        country = country,
        imageUrl = imageUrl
    )
}
