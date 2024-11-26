package com.vraj.cityconnect.controller

import com.vraj.cityconnect.request.AddUpdateEventRequest
import com.vraj.cityconnect.request.DeleteEventRequest
import com.vraj.cityconnect.response.BooleanResponseBody
import com.vraj.cityconnect.response.GetEventsResponse
import com.vraj.cityconnect.service.EventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/event")
class EventController(private val eventService: EventService) {

    @PostMapping("/add")
    fun addEvent(@RequestBody request: AddUpdateEventRequest): ResponseEntity<BooleanResponseBody> {
        val body = eventService.addEvent(request)
        return ResponseEntity.ok(body)
    }

    @GetMapping("/get")
    fun getEvents(): ResponseEntity<GetEventsResponse> {
        val body = eventService.getAllEvents()
        return ResponseEntity.ok(body)
    }

    @PostMapping("/update")
    fun updateEvent(@RequestBody request: AddUpdateEventRequest): ResponseEntity<BooleanResponseBody> {
        val body = eventService.updateEvent(request)
        return ResponseEntity.ok(body)
    }

    @PostMapping("/delete")
    fun deleteEvent(@RequestBody request: DeleteEventRequest): ResponseEntity<BooleanResponseBody> {
        val body = eventService.deleteEvent(request)
        return ResponseEntity.ok(body)
    }
}
