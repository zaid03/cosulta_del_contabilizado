package com.example.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    private JwtAuthFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthFilter(jwtUtil);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_optionsRequest_returns200() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/protected");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(200, response.getStatus());
    }

    @Test
    void doFilterInternal_cssFile_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/styles.css");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_jsFile_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/main.js");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_htmlFile_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/index.html");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_pngFile_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/logo.png");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_assetsFolder_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/assets/images/icon.png");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_rootPath_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_loginEndpoint_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/scap/api/login");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_casEndpoint_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/scap/api/cas");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_filterEndpoint_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/scap/api/filter");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_validateUsercodEndpoint_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/scap/api/validate-usucod");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_healthEndpoint_passesWithoutAuth() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/scap/health");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_protectedEndpointWithoutAuth_returns401() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/con/fetch-contratos/1/E1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_protectedEndpointWithInvalidBearer_returns401() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Basic xyz");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_protectedEndpointWithMalformedBearer_returns401() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_protectedEndpointWithValidToken_allowsAccess() throws ServletException, IOException {
        when(jwtUtil.validateAndGetSubject("valid_token")).thenReturn("testuser");

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid_token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void doFilterInternal_protectedEndpointWithValidToken_chainsRequest() throws ServletException, IOException {
        when(jwtUtil.validateAndGetSubject("valid_token")).thenReturn("testuser");

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/con/fetch-contratos/1/E1");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid_token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertTrue(chain.getRequest() != null);
    }

    @Test
    void doFilterInternal_protectedEndpointWithJwtException_returns401() throws ServletException, IOException {
        when(jwtUtil.validateAndGetSubject("invalid_token")).thenThrow(new RuntimeException("Invalid token"));

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid_token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_protectedEndpointWithNullUser_returns401() throws ServletException, IOException {
        when(jwtUtil.validateAndGetSubject("null_user_token")).thenReturn(null);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer null_user_token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(401, response.getStatus());
    }

    @Test
    void doFilterInternal_jwtException_clearsSecurityContext() throws ServletException, IOException {
        when(jwtUtil.validateAndGetSubject("bad_token")).thenThrow(new RuntimeException("JWT error"));

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer bad_token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_multipleValidTokens_createsCorrectContextForEach() throws ServletException, IOException {
        when(jwtUtil.validateAndGetSubject("token1")).thenReturn("user1");
        when(jwtUtil.validateAndGetSubject("token2")).thenReturn("user2");

        MockHttpServletRequest request1 = new MockHttpServletRequest("GET", "/api/protected");
        request1.addHeader(HttpHeaders.AUTHORIZATION, "Bearer token1");
        MockHttpServletResponse response1 = new MockHttpServletResponse();
        FilterChain chain1 = new MockFilterChain();

        filter.doFilterInternal(request1, response1, chain1);
        assertEquals("user1", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        SecurityContextHolder.clearContext();
        MockHttpServletRequest request2 = new MockHttpServletRequest("GET", "/api/protected");
        request2.addHeader(HttpHeaders.AUTHORIZATION, "Bearer token2");
        MockHttpServletResponse response2 = new MockHttpServletResponse();
        FilterChain chain2 = new MockFilterChain();

        filter.doFilterInternal(request2, response2, chain2);
        assertEquals("user2", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void doFilterInternal_bearerHeaderCaseInsensitive_allowsLowercaseBearer() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/protected");
        request.addHeader(HttpHeaders.AUTHORIZATION, "bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(401, response.getStatus()); 
    }

    @Test
    void doFilterInternal_optionsRequestCaseInsensitive() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("options", "/api/protected");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        assertEquals(200, response.getStatus());
    }
}