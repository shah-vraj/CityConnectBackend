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
import java.time.LocalDateTime

@Entity
@Table(name = "event")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false)
    val description: String = "",

    @Column(nullable = true)
    val startTime: LocalDateTime? = null,

    @Column(nullable = true)
    val endTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val country: Country = Country.OTHER,

    @Column(nullable = true)
    val imageUrl: String? = null
)
