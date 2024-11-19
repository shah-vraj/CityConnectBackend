package com.vraj.cityconnect.config

import com.vraj.cityconnect.util.JBCryptPasswordEncoder
import com.vraj.cityconnect.util.PasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    @Bean
    fun getPasswordEncoder(): PasswordEncoder = JBCryptPasswordEncoder()
}
