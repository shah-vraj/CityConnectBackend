package com.vraj.cityconnect.util

import org.mindrot.jbcrypt.BCrypt

interface PasswordEncoder {

    fun hashPassword(password: String): String

    fun checkPassword(plainPassword: String, hashedPassword: String): Boolean
}

class JBCryptPasswordEncoder : PasswordEncoder {

    override fun hashPassword(password: String): String =
        BCrypt.hashpw(password, BCrypt.gensalt())

    override fun checkPassword(plainPassword: String, hashedPassword: String): Boolean =
        BCrypt.checkpw(plainPassword, hashedPassword)
}
