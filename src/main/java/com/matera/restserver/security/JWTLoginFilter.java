package com.matera.restserver.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		AccountCredentials credentials;
		try {
			credentials = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
		} catch (IOException e) {
			/**
			 * I had a little bit of trouble here... I couldn't find a simple way to return
			 * a bad request here (since parse failed, the body of the request body is not
			 * what was expected). But to avoid 'ugly' errors, let's just consider that the
			 * user informed an invalid user/password. This will trigger a 403 response,
			 * which is not the right response :(
			 */
			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList()));
		}

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) {

		TokenAuthenticationService.addAuthentication(response, auth.getName());
	}

}
