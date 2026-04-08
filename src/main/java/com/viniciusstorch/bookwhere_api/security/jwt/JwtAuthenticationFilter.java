package com.viniciusstorch.bookwhere_api.security.jwt;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.viniciusstorch.bookwhere_api.security.details.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.extractClaims(token);

            Long id = claims.get("id", Long.class);
            String email = claims.getSubject();
            String role = claims.get("role", String.class);
            String name = claims.get("name", String.class);

            if (id == null || email == null || role == null) {
                sendUnauthorized(response, "Invalid token claims");
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                CustomUserDetails userDetails = new CustomUserDetails(
                        id,
                        email,
                        name,
                        role);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (ExpiredJwtException e) {
            request.setAttribute("auth_error", "Invalid or expired token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (JwtException e) {
            request.setAttribute("auth_error", "Invalid or expired token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            request.setAttribute("auth_error", "Authentication failed");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
