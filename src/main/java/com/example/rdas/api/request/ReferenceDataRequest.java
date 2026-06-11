package com.example.rdas.api.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReferenceDataRequest(@NotBlank String service, @NotNull JsonNode data) {
}
