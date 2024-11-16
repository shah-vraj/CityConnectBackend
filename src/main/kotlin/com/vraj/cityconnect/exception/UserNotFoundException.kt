package com.vraj.cityconnect.exception

class UserNotFoundException(email: String) :
    RuntimeException("User not found with email: $email")
