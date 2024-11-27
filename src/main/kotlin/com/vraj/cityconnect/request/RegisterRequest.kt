package com.vraj.cityconnect.request

import com.vraj.cityconnect.enums.Country
import com.vraj.cityconnect.model.User
import com.vraj.cityconnect.util.PasswordEncoder
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message = "Full name cannot be blank")
    val userName: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String,

    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    val country: Country,
) {

    fun toUser(passwordEncoder: PasswordEncoder): User = User(
        name = userName,
        country = country,
        email = email,
        password = passwordEncoder.hashPassword(password)
    )
}
