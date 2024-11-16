package com.vraj.cityconnect.controller

import com.vraj.cityconnect.request.LoginRequest
import com.vraj.cityconnect.request.RegisterRequest
import com.vraj.cityconnect.response.LoginResponse
import com.vraj.cityconnect.response.RegisterResponse
import com.vraj.cityconnect.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        val body = authService.registerUser(request)
        return ResponseEntity.ok(body)
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val body = authService.loginUser(request)
        return ResponseEntity.ok(body)
    }
}
