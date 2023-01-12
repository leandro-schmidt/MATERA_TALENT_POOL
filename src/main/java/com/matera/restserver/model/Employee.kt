package com.matera.restserver.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Employee(
    @Id
    @GeneratedValue
    var id: Long?,
    var firstName: String?,
    var middleName: String?,
    var lastName: String?,
    var dateOfBirth: LocalDateTime?,
    var dateOfEmployment: LocalDateTime?,
    var status: String?
)