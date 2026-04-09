package com.example.backend.controller;

import com.example.backend.config.TestSecurityConfig;
import com.example.backend.config.TestExceptionHandler;
import com.example.backend.config.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class, TestExceptionHandler.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validateCASTicket_returns200OnSuccessfulValidation() throws Exception {
        String testToken = "test-jwt-token-12345";
        when(jwtUtil.generate("testuser")).thenReturn(testToken);

        Map<String, String> request = Map.of(
            "username", "testuser",
            "validated", "true"
        );

        mockMvc.perform(post("/api/cas/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.username").value("testuser"))
            .andExpect(jsonPath("$.token").value(testToken));
    }

    @Test
    void validateCASTicket_returns400WhenUsernameNull() throws Exception {
        Map<String, String> request = Map.of(
            "validated", "true"
        );

        mockMvc.perform(post("/api/cas/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.Error", containsString("Nombre de usuario faltante")));
    }

    @Test
    void validateCASTicket_returns400WhenValidatedFalse() throws Exception {
        Map<String, String> request = Map.of(
            "username", "testuser",
            "validated", "false"
        );

        mockMvc.perform(post("/api/cas/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.Error", containsString("no validado")));
    }

}
