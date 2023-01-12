package com.matera.restserver.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.matera.restserver.dto.EmployeeDTO
import com.matera.restserver.service.EmployeeService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.IOException

@Configuration
open class InitialIngestionConfiguration {

//    @Bean
//    open fun runner(service: EmployeeService): CommandLineRunner {
//        return CommandLineRunner {
//            val mapper = ObjectMapper().registerModule(KotlinModule())
//            val fileContent = File("/employees.json")
//            try {
//
//                val employees : List<EmployeeDTO> = mapper.readValue(fileContent)
//                service.create(employees)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }
}