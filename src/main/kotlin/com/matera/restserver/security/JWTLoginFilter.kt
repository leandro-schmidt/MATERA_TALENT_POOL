package com.matera.restserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(url: String?, authManager: AuthenticationManager?) :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {
    init {
        authenticationManager = authManager
    }

    private val logger = LoggerFactory.getLogger(JWTLoginFilter::class.java)

    @Throws(ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials: AccountCredentials = try {
            ObjectMapper().readValue(request.inputStream, AccountCredentials::class.java)
        } catch (e: IOException) {
            /**
             * I had a little bit of trouble here... I couldn't find a simple way to return
             * a bad request here (since parse failed, the body of the request body is not
             * what was expected). But to avoid 'ugly' errors, let's just consider that the
             * user informed an invalid user/password. This will trigger a 403 response,
             * which is not the right response :(
             */
            logger.error(e.message)
            return authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(null, null, emptyList()))
        }
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                credentials.username,
                credentials.password,
                emptyList()
            )
        )
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
        auth: Authentication
    ) {
        TokenAuthenticationService.addAuthentication(response, auth.name)
    }
}
