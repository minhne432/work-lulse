package com.workpulse.workpulse.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(autho -> autho.getAuthority().equals("ROLE_ADMIN"));

        boolean isEmployee = authorities.stream()
                .anyMatch(autho -> autho.getAuthority().equals("ROLE_EMPLOYEE"));

        String redirectUrl = "/";

        if (isAdmin) {
            redirectUrl = "/admin/dashboard";
        } else if (isEmployee) {
            redirectUrl = "/employee/dashboard";
        }
        System.out.println("Redirecting to: " + redirectUrl);
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
