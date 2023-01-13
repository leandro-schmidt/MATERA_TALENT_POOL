package com.matera.restserver.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

private const val INVALID_ID = -999L

@Entity
data class Employee(
    @Id
    @GeneratedValue
    var id: Long = INVALID_ID,
    var firstName: String = "",
    var middleName: String = "",
    var lastName: String = "",
    var dateOfBirth: LocalDateTime = LocalDateTime.MAX,
    var dateOfEmployment: LocalDateTime = LocalDateTime.MAX,
    var status: String = ""
)
