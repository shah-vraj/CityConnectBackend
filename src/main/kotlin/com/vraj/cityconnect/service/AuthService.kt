package com.vraj.cityconnect.service

import com.vraj.cityconnect.exception.PasswordNotMatchingException
import com.vraj.cityconnect.exception.UserAlreadyRegisteredException
import com.vraj.cityconnect.exception.UserNotFoundException
import com.vraj.cityconnect.repository.UserRepository
import com.vraj.cityconnect.request.LoginRequest
import com.vraj.cityconnect.request.RegisterRequest
import com.vraj.cityconnect.response.LoginResponse
import com.vraj.cityconnect.response.RegisterResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

interface AuthService {

    fun registerUser(request: RegisterRequest): RegisterResponse

    fun loginUser(request: LoginRequest): LoginResponse
}

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : AuthService {

    override fun registerUser(request: RegisterRequest): RegisterResponse {
        if (userRepository.existsByEmail(request.email))
            throw UserAlreadyRegisteredException(request.email)

        userRepository.save(request.toUser(passwordEncoder))
        return RegisterResponse.success()
    }

    override fun loginUser(request: LoginRequest): LoginResponse {
        userRepository.findByEmail(request.email)?.let {
            if (!passwordEncoder.matches(request.password, it.password))
                throw PasswordNotMatchingException(request.email)
        } ?: throw UserNotFoundException(request.email)
        return LoginResponse.success()
    }
}
