package com.example.rdas.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReferenceDataSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void requiresAuthenticationByDefault() throws Exception {
        mockMvc.perform(post("/api/v1/reference-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "service": "reference.continent.list",
                                  "data": {}
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
    }
}
