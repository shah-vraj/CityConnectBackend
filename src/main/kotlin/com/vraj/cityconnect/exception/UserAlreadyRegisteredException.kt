package com.vraj.cityconnect.exception

class UserAlreadyRegisteredException(email: String) :
    RuntimeException("User already registered with email: $email")
