package com.vraj.cityconnect.exception

class PasswordNotMatchingException(email: String) :
    RuntimeException("Password not matching for email: $email")
