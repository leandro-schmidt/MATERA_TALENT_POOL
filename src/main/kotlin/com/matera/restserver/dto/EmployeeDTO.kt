package com.matera.restserver.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class EmployeeDTO(
    var id: Long,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val status: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateOfBirth: LocalDateTime,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateOfEmployment: LocalDateTime
)
