package com.matera.restserver.test

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest
