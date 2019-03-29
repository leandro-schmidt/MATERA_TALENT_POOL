package com.matera.restserver;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matera.restserver.service.EmployeeService;
import com.matera.restserver.util.Messages;
import com.matera.restserver.dto.EmployeeDTO;

@SpringBootApplication
public class RestServerApplication {

	private static final Logger LOGGER = Logger.getLogger( RestServerApplication.class.getName() );
	
	public static void main(String[] args) {
		SpringApplication.run(RestServerApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(EmployeeService service) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<EmployeeDTO>> typeReference = new TypeReference<List<EmployeeDTO>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/employees.json");
			try {
				List<EmployeeDTO> employees = mapper.readValue(inputStream,typeReference);
				service.create(employees);
				LOGGER.info(Messages.getMessage("employeescharge.success"));
			} catch (IOException e){
				LOGGER.severe(Messages.getMessage("employeescharge.fail") + e.getMessage());
			}
		};
	}
	
}
