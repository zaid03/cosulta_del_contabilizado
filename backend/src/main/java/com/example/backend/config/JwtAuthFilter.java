package com.example.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

@Component
@Profile("!test") 
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwt;
    
    private static final List<String> PUBLIC_PREFIXES = List.of(
        "/scap/**",
        "/scap/api/login",
        "/scap/api/cas",
        "/scap/api/filter",
        "/scap/api/validate-usucod",
        "/scap/health",
        "/scap/api/sical",
        "/scap/api/rpc/call",
        "/scap/login"
    );
    
    // Extensions de fichiers statiques à ignorer
    private static final List<String> STATIC_EXTENSIONS = List.of(
        ".js", ".css", ".html", ".ico", ".json", ".png", ".jpg", ".jpeg", 
        ".gif", ".svg", ".woff", ".woff2", ".ttf", ".eot"
    );

    public JwtAuthFilter(JwtUtil jwt) { 
        this.jwt = jwt; 
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String path = req.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
            return;
        }

        if (isStaticResource(path)) {
            chain.doFilter(req, res);
            return;
        }

        if (isPublic(path)) {
            chain.doFilter(req, res);
            return;
        }

        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = auth.substring(7);
        try {
            String user = jwt.validateAndGetSubject(token);
            if (user == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, java.util.List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isStaticResource(String path) {
        if (path.equals("/") || path.equals("/scap") || path.equals("/scap/") || 
            path.endsWith("/index.html")) {
            return true;
        }
        
        if (path.contains("/assets/")) {
            return true;
        }
        
        String lowerPath = path.toLowerCase();
        return STATIC_EXTENSIONS.stream().anyMatch(lowerPath::endsWith);
    }

    /**
     * Vérifie si le chemin est une API publique
     */
    private boolean isPublic(String path) {
        return PUBLIC_PREFIXES.stream().anyMatch(path::startsWith);
    }
}