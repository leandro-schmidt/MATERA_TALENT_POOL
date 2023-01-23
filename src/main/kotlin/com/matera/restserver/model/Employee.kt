package com.matera.restserver.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

private const val ZERO = 0L

@Entity
data class Employee(
    @Id
    @GeneratedValue
    var id: Long = ZERO,
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val dateOfBirth: LocalDateTime = LocalDateTime.MAX,
    val dateOfEmployment: LocalDateTime = LocalDateTime.MAX,
    val status: String = ""
)
