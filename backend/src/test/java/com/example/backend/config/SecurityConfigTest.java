package com.example.backend.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.cors.CorsConfigurationSource;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
    }

    @Test
    void cors_configurationSource_exists() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        assertNotNull(corsSource, "CORS configuration source should not be null");
    }

    @Test
    void cors_allowedOrigins_includes_localhost4200() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        var mockRequest = new MockHttpServletRequest();
        var corsConfig = corsSource.getCorsConfiguration(mockRequest);
        
        assertNotNull(corsConfig, "CORS configuration should exist");
        assertTrue(corsConfig.getAllowedOrigins().contains("http://localhost:4200"),
            "Should allow localhost:4200 origin");
    }

    @Test
    void cors_allowedOrigins_includes_localhost8080() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        var mockRequest = new MockHttpServletRequest();
        var corsConfig = corsSource.getCorsConfiguration(mockRequest);
        
        assertNotNull(corsConfig, "CORS configuration should exist");
        assertTrue(corsConfig.getAllowedOrigins().contains("http://localhost:8080"),
            "Should allow localhost:8080 origin");
    }

    @Test
    void cors_allowedMethods_includes_GET_POST_PUT_DELETE() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        var mockRequest = new MockHttpServletRequest();
        var corsConfig = corsSource.getCorsConfiguration(mockRequest);
        
        assertNotNull(corsConfig, "CORS configuration should exist");
        assertTrue(corsConfig.getAllowedMethods().contains("GET"), "Should allow GET");
        assertTrue(corsConfig.getAllowedMethods().contains("POST"), "Should allow POST");
        assertTrue(corsConfig.getAllowedMethods().contains("PUT"), "Should allow PUT");
        assertTrue(corsConfig.getAllowedMethods().contains("DELETE"), "Should allow DELETE");
    }

    @Test
    void cors_allowedMethods_includes_OPTIONS_PATCH() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        var mockRequest = new MockHttpServletRequest();
        var corsConfig = corsSource.getCorsConfiguration(mockRequest);
        
        assertNotNull(corsConfig, "CORS configuration should exist");
        assertTrue(corsConfig.getAllowedMethods().contains("OPTIONS"), "Should allow OPTIONS");
        assertTrue(corsConfig.getAllowedMethods().contains("PATCH"), "Should allow PATCH");
    }

    @Test
    void cors_allowedHeaders_includes_all() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        var mockRequest = new MockHttpServletRequest();
        var corsConfig = corsSource.getCorsConfiguration(mockRequest);
        
        assertNotNull(corsConfig, "CORS configuration should exist");
        assertTrue(corsConfig.getAllowedHeaders().contains("*"), "Should allow all headers");
    }

    @Test
    void cors_credentials_allowed() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();
        var mockRequest = new MockHttpServletRequest();
        var corsConfig = corsSource.getCorsConfiguration(mockRequest);
        
        assertNotNull(corsConfig, "CORS configuration should exist");
        assertTrue(corsConfig.getAllowCredentials(), "Should allow credentials");
    }

    @Test
    void passwordEncoder_bean_exists() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertNotNull(encoder, "Password encoder bean should not be null");
    }

    @Test
    void passwordEncoder_isBCryptPasswordEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertInstanceOf(BCryptPasswordEncoder.class, encoder, 
            "Password encoder should be BCryptPasswordEncoder");
    }

    @Test
    void passwordEncoder_encodes_password() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        String rawPassword = "testPassword123";
        
        String encodedPassword = encoder.encode(rawPassword);
        
        assertNotNull(encodedPassword, "Encoded password should not be null");
        assertNotEquals(rawPassword, encodedPassword, "Encoded password should differ from raw");
    }

    @Test
    void passwordEncoder_matches_encoded_password() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        String rawPassword = "testPassword123";
        String encodedPassword = encoder.encode(rawPassword);
        
        assertTrue(encoder.matches(rawPassword, encodedPassword),
            "Should match raw password with encoded password");
    }

    @Test
    void passwordEncoder_does_not_match_wrong_password() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        String encodedPassword = encoder.encode("correctPassword");
        
        assertFalse(encoder.matches("wrongPassword", encodedPassword),
            "Should not match wrong password with encoded password");
    }

    @Test
    void webSecurityCustomizer_ignores_root_path() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    @Test
    void webSecurityCustomizer_ignores_scap() throws Exception {
        mockMvc.perform(get("/scap"))
            .andExpect(status().isOk());
    }

    @Test
    void webSecurityCustomizer_ignores_index_html() throws Exception {
        mockMvc.perform(get("/index.html"))
            .andExpect(status().isOk());
    }

    @Test
    void securityFilterChain_requires_auth_for_generic_api_endpoint() throws Exception {
        mockMvc.perform(get("/api/protected/resource"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_requires_auth_for_factura_endpoint() throws Exception {
        mockMvc.perform(get("/api/factura/list"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_requires_auth_for_provider_endpoint() throws Exception {
        mockMvc.perform(get("/api/provider/search"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_permitsAll_root() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    @Test
    void securityFilterChain_disables_httpBasic() throws Exception {
        mockMvc.perform(get("/api/protected/resource")
            .header("Authorization", "Basic dXNlcjpwYXNz")
        )
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_cors_headers_present_on_preflight() throws Exception {
        mockMvc.perform(options("/api/factura/list")
            .header("Origin", "http://localhost:4200")
            .header("Access-Control-Request-Method", "GET")
        )
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"))
            .andExpect(header().exists("Access-Control-Allow-Methods"));
    }

    @Test
    void securityFilterChain_cors_allowed_origin_header() throws Exception {
        mockMvc.perform(options("/api/factura/list")
            .header("Origin", "http://localhost:4200")
            .header("Access-Control-Request-Method", "GET")
        )
            .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }

    @Test
    void securityFilterChain_jwt_filter_present() throws Exception {
        mockMvc.perform(get("/api/factura/list")
                .header("Authorization", "Bearer invalid"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_permits_put_on_protected() throws Exception {
        mockMvc.perform(put("/api/protected/update"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_permits_delete_on_protected() throws Exception {
        mockMvc.perform(delete("/api/protected/remove"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void securityFilterChain_permits_patch_on_protected() throws Exception {
        mockMvc.perform(patch("/api/protected/patch"))
            .andExpect(status().isUnauthorized());
    }
}
