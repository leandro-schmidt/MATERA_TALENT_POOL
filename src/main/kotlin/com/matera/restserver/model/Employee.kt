package com.matera.restserver.model

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Employee(
    @Id
    var id: UUID = UUID.randomUUID(),
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val dateOfBirth: LocalDateTime = LocalDateTime.MAX,
    val dateOfEmployment: LocalDateTime = LocalDateTime.MAX,
    val status: String = ""
)
