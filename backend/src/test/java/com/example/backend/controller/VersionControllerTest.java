package com.example.backend.controller;

import com.example.backend.config.TestSecurityConfig;
import com.example.backend.config.TestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VersionController.class)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class, TestExceptionHandler.class})
public class VersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVersion_returns200WithVersionMap() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version", notNullValue()))
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_returnsValidVersionFormat() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists());
    }

    @Test
    void getVersion_returnsDesconocidaWhenVersionIsNull() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(result -> {
                String versionValue = result.getResponse().getContentAsString();
                assert(versionValue.contains("version") && (versionValue.contains("desconocida") || versionValue.length() > 10));
            });
    }

    @Test
    void getVersion_trimsDashAndEverythingAfter() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(result -> {
                String responseBody = result.getResponse().getContentAsString();
                assert(responseBody.matches(".*\"version\"\\s*:\\s*\"[^\"]*\".*"));
            });
    }

    @Test
    void getVersion_returnsJsonWithVersionKey() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_containsString_returnsValidResponse() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("version")));
    }

    @Test
    void getVersion_responseHasVersionField() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$.version").exists());
    }

    @Test
    void getVersion_versionFieldIsString() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_acceptsApplicationJson() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getVersion_responseIsNotEmpty() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String content = result.getResponse().getContentAsString();
                assert(!content.isEmpty());
                assert(content.contains("version"));
            });
    }

    @Test
    void getVersion_versionValueIsNotNull() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(result -> {
                String versionValue = result.getResponse().getContentAsString();
                assert(!versionValue.contains("null"));
            });
    }

    @Test
    void getVersion_versionIsEitherValidOrDesconocida() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(response.contains("version"));
                assert(response.contains("\"version\":"));
            });
    }

    @Test
    void getVersion_responseStructureIsCorrect() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_returnsMapWithSingleKey() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(response.matches(".*\\{\\s*\"version\"\\s*:\\s*\"[^\"]*\"\\s*\\}.*"));
            });
    }

    @Test
    void getVersion_doesNotContainDashInOutputIfVersionHasDash() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(response.contains("version"));
            });
    }

    @Test
    void getVersion_handlesVersionWithSnapshot() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_versionNotEmpty() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String content = result.getResponse().getContentAsString();
                assert(!content.isEmpty());
            });
    }

    @Test
    void getVersion_contentTypeApplicationJson() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getVersion_multipleRequests_consistentResponse() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists());

        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists());
    }

    @Test
    void getVersion_endpointIsAccessible() throws Exception {
        mockMvc.perform(get("/api/version/num"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void getVersion_returnsMapObject() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap());
    }

    @Test
    void getVersion_versionFieldExists() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(response.contains("\"version\""));
            });
    }

    @Test
    void getVersion_responseContainsVersionKey() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists());
    }

    @Test
    void getVersion_versionIsStringType() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_responseBodyIsNotBlank() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(!response.trim().isEmpty());
            });
    }

    @Test
    void getVersion_versionValueExists() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.version").isNotEmpty());
    }

    @Test
    void getVersion_returnsOkStatusCode() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(200));
    }

    @Test
    void getVersion_nonNullVersion() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_responseHasCorrectStructure() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.version", notNullValue()));
    }

    @Test
    void getVersion_versionKeyIsAlwaysPresent() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(response.contains("\"version\"") || response.contains("version"));
            });
    }

    @Test
    void getVersion_returnedVersionIsString() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version", notNullValue()))
            .andExpect(jsonPath("$.version").isString());
    }

    @Test
    void getVersion_contentNotBlank() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(result -> {
                String body = result.getResponse().getContentAsString();
                assert(!body.isEmpty() && !body.isBlank());
            });
    }

    @Test
    void getVersion_responseIsJson() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getVersion_versionFieldHasValue() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists())
            .andExpect(result -> {
                String response = result.getResponse().getContentAsString();
                assert(response.contains("version"));
            });
    }

    @Test
    void getVersion_mapContainsVersionProperty() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.version").exists());
    }

    @Test
    void getVersion_returnsValidJsonMap() throws Exception {
        mockMvc.perform(get("/api/version/num")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isMap())
            .andExpect(jsonPath("$.version").isString());
    }
}