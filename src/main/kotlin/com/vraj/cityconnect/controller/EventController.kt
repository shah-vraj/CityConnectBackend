package com.vraj.cityconnect.controller

import com.vraj.cityconnect.request.AddUpdateEventRequest
import com.vraj.cityconnect.request.DeleteEventRequest
import com.vraj.cityconnect.response.BooleanResponseBody
import com.vraj.cityconnect.response.GetEventsResponse
import com.vraj.cityconnect.service.EventService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/v1/event")
class EventController(private val eventService: EventService) {

    @PostMapping("/add", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addEvent(
        @RequestPart("event") eventRequest: AddUpdateEventRequest,
        @RequestPart("image", required = false) image: MultipartFile?
    ): ResponseEntity<BooleanResponseBody> {
        val body = eventService.addEvent(eventRequest.copy(image = image))
        return ResponseEntity.ok(body)
    }

    @GetMapping("/get")
    fun getEvents(): ResponseEntity<GetEventsResponse> {
        val body = eventService.getAllEvents()
        return ResponseEntity.ok(body)
    }

    @PostMapping("/update", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateEvent(
        @RequestPart("event") eventRequest: AddUpdateEventRequest,
        @RequestPart("image", required = false) image: MultipartFile?
    ): ResponseEntity<BooleanResponseBody> {
        val body = eventService.updateEvent(eventRequest.copy(image = image))
        return ResponseEntity.ok(body)
    }

    @PostMapping("/delete")
    fun deleteEvent(@RequestBody request: DeleteEventRequest): ResponseEntity<BooleanResponseBody> {
        val body = eventService.deleteEvent(request)
        return ResponseEntity.ok(body)
    }
}
