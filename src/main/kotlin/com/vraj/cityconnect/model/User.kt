package com.vraj.cityconnect.model

import com.vraj.cityconnect.enums.Country
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val country: Country = Country.OTHER,

    @Column(nullable = false)
    val email: String = "",

    @Column(nullable = false)
    val password: String = ""
)
