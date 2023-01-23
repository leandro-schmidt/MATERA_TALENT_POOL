package com.matera.restserver.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class CreateEmployeeDTO(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val status: String = "",
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateOfBirth: LocalDateTime = LocalDateTime.MAX,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateOfEmployment: LocalDateTime = LocalDateTime.MAX
)
