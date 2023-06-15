package com.restAPI.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        final Map<String, Object> body = new HashMap<>();
        body.put("content-type", MediaType.APPLICATION_JSON_VALUE);
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("error", "Unauthorized");
        body.put("message", "Authentication failed");
        body.put("path", request.getRequestURI());

        System.out.println(body);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
