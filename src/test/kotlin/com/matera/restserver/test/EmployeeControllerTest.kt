package com.matera.restserver.test

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.matera.restserver.model.Employee
import com.matera.restserver.service.EmployeeService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var employeeService: EmployeeService

    private lateinit var allEmployees: List<Employee>

    private val listOfEmployeeTypeReference: TypeReference<List<Employee?>?> =
        object : TypeReference<List<Employee?>?>() {}

    val mapper = ObjectMapper().registerModule(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())

    val employeeJson = """
            {
              "firstName" : "Something completely different" ,
              "middleName" : "Mashed Potatoes" ,
              "lastName" : "This is not a name" ,
              "dateOfBirth" : "1992-07-18T00:00Z" ,
              "dateOfEmployment" : "2017-12-12T00:00Z",
              "status" : "ACTIVE"
            }
    """.trimIndent()

    @Nested
    inner class FindAllEmployees {
        @Test
        fun `Get all employees should return ok and the list of actual employees`() {
            allEmployees = employeeService.findAll()
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get(
                    "/employee"
                )
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            assertEquals(allEmployees, mapper.readValue(result.response.contentAsString, listOfEmployeeTypeReference))
        }
    }

    @Nested
    inner class FindEmployee {
        @Test
        fun `Get employee with invalid ID should return ok and empty response`() {
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get(
                    "/employee/${UUID.randomUUID()}"
                )
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            assertEquals(result.response.contentAsString.length, 0)
        }

        @Test
        fun `Get employee with valid ID should return ok and actual employee`() {
            allEmployees = employeeService.findAll()
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get(
                    "/employee/${allEmployees[0].id}"
                )
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            val employee = mapper.readValue(result.response.contentAsString, Employee::class.java)
            assertEquals(employee, allEmployees[0])
        }
    }

    @Nested
    inner class CreateEmployee {

        @Test
        fun `Create an employee should return ok and the employee should be created`() {
            val employeesBeforeTest = employeeService.findAll().size
            val result = mockMvc.perform(
                MockMvcRequestBuilders.post(
                    "/employee"
                ).content(employeeJson)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()

            val employeesAfterTest = employeeService.findAll().size
            assertEquals(employeesBeforeTest + 1, employeesAfterTest)
        }
    }

    @Nested
    inner class UpdateEmployee {

        @Test
        fun `Update an existing employee should return ok and the employee should be updated`() {
            allEmployees = employeeService.findAll()
            val result = mockMvc.perform(
                MockMvcRequestBuilders.put(
                    "/employee/${allEmployees[0].id}"
                ).content(employeeJson)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            val employee = employeeService.find(allEmployees[0].id)
            val jsonEmployee = mapper.readValue(employeeJson, Employee::class.java)
            assertEquals(employee?.firstName, jsonEmployee.firstName)
            assertEquals(employee?.middleName, jsonEmployee.middleName)
            assertEquals(employee?.lastName, jsonEmployee.lastName)
        }

        @Test
        fun `Update an employee that did not exist should return bad request`() {
            mockMvc.perform(
                MockMvcRequestBuilders.put(
                    "/employee/${UUID.randomUUID()}"
                ).content(employeeJson)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()
        }
    }

    @Nested
    inner class DeleteEmployee {

        @Test
        fun `Delete an existing employee should return ok and delete the employee`() {
            val employeesBeforeTest = employeeService.findAll().size
            allEmployees = employeeService.findAll()
            mockMvc.perform(
                MockMvcRequestBuilders.delete(
                    "/employee/${allEmployees[0].id}"
                )
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

            val employee = employeeService.find(allEmployees[0].id)
            val employeesAfterTest = employeeService.findAll().size

            assertNull(employee)
            assertEquals(employeesBeforeTest - 1, employeesAfterTest)
        }

        @Test
        fun `Delete an non existing employee should return ok`() {
            mockMvc.perform(
                MockMvcRequestBuilders.delete(
                    "/employee/${UUID.randomUUID()}"
                )
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
        }
    }
}
