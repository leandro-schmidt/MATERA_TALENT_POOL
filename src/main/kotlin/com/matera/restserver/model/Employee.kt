package com.matera.restserver.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Employee(
    @Id
    @GeneratedValue
    var id: Long = -999,
    var firstName: String = "",
    var middleName: String = "",
    var lastName: String = "",
    var dateOfBirth: LocalDateTime = LocalDateTime.MAX,
    var dateOfEmployment: LocalDateTime = LocalDateTime.MAX,
    var status: String = ""
)