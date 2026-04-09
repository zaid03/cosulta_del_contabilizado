package com.example.backend.controller;

import com.example.backend.config.TestSecurityConfig;
import com.example.backend.dto.EntNomProjection;
import com.example.backend.sqlserver1.repository.EntRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EntController.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class EntControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntRepository entRepository;

    @Test
    void getEntName_found_returns200() throws Exception {
        EntNomProjection proj = mock(EntNomProjection.class);
        when(entRepository.findProjectedByENTCOD(1)).thenReturn(Optional.of(proj));

        mockMvc.perform(get("/api/ent/name/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(not(isEmptyString())));

        verify(entRepository).findProjectedByENTCOD(1);
    }

    @Test
    void getEntName_notFound_returns404() throws Exception {
        when(entRepository.findProjectedByENTCOD(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ent/name/2")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("Sin resultado")));

        verify(entRepository).findProjectedByENTCOD(2);
    }

    @Test
    void getEntName_exception_returns400() throws Exception {
        when(entRepository.findProjectedByENTCOD(anyInt())).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/api/ent/name/3")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Error:")));

        verify(entRepository).findProjectedByENTCOD(3);
    }
}