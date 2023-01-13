package com.matera.restserver.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object TokenAuthenticationService {
    private const val EXPIRATION_TIME: Long = 86000000

    // Really secret :X
    private const val SECRET = "MySuperDuperSecretSecret"
    private const val TOKEN_PREFIX = "Bearer"
    private const val HEADER_STRING = "Authorization"
    fun addAuthentication(response: HttpServletResponse, username: String?) {
        val jwt = Jwts.builder().setSubject(username) // Can't use LocalDate =(
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET).compact()
        response.addHeader(HEADER_STRING, "$TOKEN_PREFIX $jwt")
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            val user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).body
                .subject
            if (user != null) {
                return UsernamePasswordAuthenticationToken(user, null, emptyList())
            }
        }
        return null
    }
}
