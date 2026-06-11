package com.example.rdas.application.query;

import com.example.rdas.api.request.PageRequestData;
import com.example.rdas.application.cqrs.Query;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CountriesByCurrencyQuery(
        @NotBlank String currencyCode,
        @Min(0) Integer page,
        @Min(1) @Max(100) Integer size,
        @Pattern(regexp = "countryName|countryCode|continentName|currencyCode") String sortBy,
        @Pattern(regexp = "(?i)ASC|DESC") String sortDirection) implements Query {

    public static final String COMMAND_NAME = "reference.country.currency";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    public PageRequestData pageRequest() {
        return new PageRequestData(page, size, sortBy, sortDirection);
    }
}
