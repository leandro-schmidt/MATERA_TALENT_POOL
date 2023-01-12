package com.matera.restserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ComponentScan("com.matera.restserver")
@EnableJpaRepositories("com.matera.restserver.repository")
@EntityScan("com.matera.restserver.model")
@ConfigurationPropertiesScan
@SpringBootApplication
open class RestServerApplication
    fun main(args: Array<String>) {
        runApplication<RestServerApplication>(*args)
    }