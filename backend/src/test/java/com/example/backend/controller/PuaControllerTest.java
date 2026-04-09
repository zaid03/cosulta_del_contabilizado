package com.example.backend.controller;

import com.example.backend.config.TestSecurityConfig;
import com.example.backend.sqlserver1.model.Pua;
import com.example.backend.sqlserver1.repository.PuaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;

@WebMvcTest(controllers = PuaController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class PuaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PuaRepository puaRepository;

    @Test
    void shouldReturnNotFoundWhenNoResults() throws Exception {
        when(puaRepository.findByUSUCODAndAPLCOD("no-user", 7)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/pua/filter/no-user")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("Sin resultado")));

        verify(puaRepository).findByUSUCODAndAPLCOD("no-user", 7);
    }

    @Test
    void shouldReturnOkWithResults() throws Exception {
        Pua p = new Pua();
        when(puaRepository.findByUSUCODAndAPLCOD("user1", 7)).thenReturn(List.of(p));

        mockMvc.perform(get("/api/pua/filter/user1")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        verify(puaRepository).findByUSUCODAndAPLCOD("user1", 7);
    }

    @Test
    void shouldReturnBadRequestOnException() throws Exception {
        when(puaRepository.findByUSUCODAndAPLCOD("err", 7)).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/api/pua/filter/err")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Error:")));

        verify(puaRepository).findByUSUCODAndAPLCOD("err", 7);
    }
}