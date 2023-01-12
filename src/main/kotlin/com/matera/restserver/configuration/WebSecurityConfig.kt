package com.matera.restserver.configuration

import com.matera.restserver.security.JWTAuthenticationFilter
import com.matera.restserver.security.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
            .anyRequest().authenticated().and()
            .addFilterBefore(
                JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            /**
             * The '{noop}' will tell spring to use NoOpPasswordEncoder, which is
             * deprecated. Since the idea is to keep things simple, I think it's ok to use
             * it here. Doesn't mean I would use it in a 'real' application, though; I think
             * the idea here is exercise the use of the client token authentication
             * solution, this is just a detail :)
             */
            .withUser("admin").password("{noop}password").roles("ADMIN")
    }
}