package com.example.backend.controller;

import com.example.backend.config.TestSecurityConfig;
import com.example.backend.config.TestExceptionHandler;
import com.example.backend.dto.MenuDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.backend.sqlserver1.repository.RpmRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(controllers = RpmController.class)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class, TestExceptionHandler.class})
public class RpmControllerTest {
     @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RpmRepository rpmRepository;
    
    @Test
    void shouldReturnNotFoundWhenNoMenus() throws Exception {
        when(rpmRepository.findByPERCODAndAPLCOD("USER1", 7)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/mnucods").param("PERCOD", "USER1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("Sin resultado")));

        verify(rpmRepository).findByPERCODAndAPLCOD("USER1", 7);
    }

    @Test
    void shouldReturnBadRequestWhenDataAccessExceptionThrown() throws Exception {
        when(rpmRepository.findByPERCODAndAPLCOD(anyString(), anyInt()))
            .thenThrow(new DataAccessResourceFailureException("DB down"));

        mockMvc.perform(get("/api/mnucods").param("PERCOD", "X")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Error:")));
    }

    @Test
    void shouldReturnMenuDtos_whenRepositoryReturnsMenus() throws Exception {
        MenuDto menu1 = new MenuDto("MENU1");
        MenuDto menu2 = new MenuDto("MENU2");

        when(rpmRepository.findByPERCODAndAPLCOD("USER1", 7)).thenReturn(List.of(menu1, menu2));

        mockMvc.perform(get("/api/mnucods")
                .param("PERCOD", "USER1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].mnucod", is("MENU1")))
            .andExpect(jsonPath("$[1].mnucod", is("MENU2")));

        verify(rpmRepository).findByPERCODAndAPLCOD("USER1", 7);
    }
}
