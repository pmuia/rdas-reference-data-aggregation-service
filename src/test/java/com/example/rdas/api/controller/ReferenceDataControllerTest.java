package com.example.rdas.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ReferenceDataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchesCountriesWithFiltersSortingAndPagination() throws Exception {
        mockMvc.perform(post("/api/v1/reference-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "service": "reference.country.search",
                                  "data": {
                                    "continentCode": "AF",
                                    "page": 0,
                                    "size": 2,
                                    "sortBy": "countryName",
                                    "sortDirection": "DESC"
                                  }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.totalElements").value(3))
                .andExpect(jsonPath("$.data.countries[0].countryCode").value("UG"))
                .andExpect(jsonPath("$.data.countries.length()").value(2));
    }

    @Test
    void returnsCountryDetails() throws Exception {
        mockMvc.perform(post("/api/v1/reference-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "service": "reference.country.details",
                                  "data": {"countryCode": "KE"}
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.countryName").value("Kenya"))
                .andExpect(jsonPath("$.data.currencyCode").value("KES"));
    }

    @Test
    void validatesServiceSpecificData() throws Exception {
        mockMvc.perform(post("/api/v1/reference-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "service": "reference.country.details",
                                  "data": {}
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void rejectsUnsupportedServices() throws Exception {
        mockMvc.perform(post("/api/v1/reference-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "service": "reference.unknown",
                                  "data": {}
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("UNSUPPORTED_SERVICE"));
    }

    @Test
    void refreshesReferenceData() throws Exception {
        mockMvc.perform(post("/api/v1/reference-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "service": "reference.data.refresh",
                                  "data": {"refreshType": "FULL"}
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.refreshStatus").value("COMPLETED"));
    }
}
