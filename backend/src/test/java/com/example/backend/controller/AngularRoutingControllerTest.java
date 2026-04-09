package com.example.backend.controller;

import com.example.backend.config.TestSecurityConfig;
import com.example.backend.config.TestExceptionHandler;
import com.example.backend.controller.AngularRoutingController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AngularRoutingController.class)
@ActiveProfiles("test")
@Import({TestSecurityConfig.class, TestExceptionHandler.class})
@DisplayName("AngularRoutingController Tests")
public class AngularRoutingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Single path segment without dot should forward to index.html")
    void singleSegmentNoDot_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/dashboard"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Single path segment with hyphen should forward to index.html")
    void singleSegmentWithHyphen_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/user-profile"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Single path segment with underscore should forward to index.html")
    void singleSegmentWithUnderscore_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/admin_section"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Single path segment with numbers should forward to index.html")
    void singleSegmentWithNumbers_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/section123"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Single path segment with mixed alphanumeric should forward to index.html")
    void singleSegmentMixedAlphanumeric_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/api2v3"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Single character path should forward to index.html")
    void singleCharacterPath_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/a"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Long single path segment should forward to index.html")
    void longSinglePathSegment_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/verylongpathwithnodotshere"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Path with special characters (dash, underscore, numbers) should forward to index.html")
    void pathWithSpecialCharacters_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/user-admin_section123"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Path starting with number should forward to index.html")
    void pathStartingWithNumber_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/123dashboard"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Path with only special characters (no dot, no slash) should forward to index.html")
    void pathWithSpecialCharsOnly_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/---"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("Path with dot in single segment should NOT match pattern (returns 500)")
    void pathWithDot_shouldNotMatch() throws Exception {
        mockMvc.perform(get("/script.js"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Path with multiple dots should NOT match pattern (returns 500)")
    void pathWithMultipleDots_shouldNotMatch() throws Exception {
        mockMvc.perform(get("/style.min.css"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Image file path should NOT match pattern (returns 500)")
    void imageFile_shouldNotMatch() throws Exception {
        mockMvc.perform(get("/logo.png"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("CSS file path should NOT match pattern (returns 500)")
    void cssFile_shouldNotMatch() throws Exception {
        mockMvc.perform(get("/styles.css"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Multi-segment path should trigger error handling (not matched by this controller)")
    void multiSegmentPath_notHandledByController() throws Exception {
        mockMvc.perform(get("/dashboard/overview"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Nested path with API segment should trigger error handling")
    void nestedApiPath_notHandledByController() throws Exception {
        mockMvc.perform(get("/admin/users/list"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Path with trailing slash should trigger error handling")
    void pathWithTrailingSlash_notHandledByController() throws Exception {
        mockMvc.perform(get("/dashboard/"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deep nested path should trigger error handling")
    void deepNestedPath_notHandledByController() throws Exception {
        mockMvc.perform(get("/a/b/c/d/e/f"))
            .andDo(print())
            .andExpect(status().isInternalServerError());
    }
}
