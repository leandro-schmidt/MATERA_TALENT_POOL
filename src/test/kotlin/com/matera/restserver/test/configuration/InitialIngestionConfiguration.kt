package com.matera.restserver.test.configuration

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.matera.restserver.dto.CreateEmployeeDTO
import com.matera.restserver.service.EmployeeService
import com.matera.restserver.util.Messages
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStream

@Component
@Profile("test")
open class InitialIngestionConfiguration {

    private val logger = LoggerFactory.getLogger(InitialIngestionConfiguration::class.java)

    fun listOfTestEmployees(): List<CreateEmployeeDTO> =
        try {
            val mapper = ObjectMapper().registerModule(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            val typeReference: TypeReference<List<CreateEmployeeDTO?>?> =
                object : TypeReference<List<CreateEmployeeDTO?>?>() {}
            val inputStream: InputStream = TypeReference::class.java.getResourceAsStream("/employees_test.json")
            val employees: List<CreateEmployeeDTO> = mapper.readValue(inputStream, typeReference)
                as List<CreateEmployeeDTO>
            employees
        } catch (e: IOException) {
            logger.error(Messages.getMessage("employeescharge.fail"), e.message)
            listOf(CreateEmployeeDTO())
        }

    @Bean
    open fun runner(service: EmployeeService): CommandLineRunner {
        return CommandLineRunner {
            service.create(listOfTestEmployees())
            logger.info(Messages.getMessage("employeescharge.success"))
        }
    }
}
