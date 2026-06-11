package com.example.rdas.application.query;

import com.example.rdas.application.cqrs.Query;
import jakarta.validation.constraints.NotBlank;

public record CountryDetailsQuery(@NotBlank String countryCode) implements Query {
    public static final String NAME = "reference.country.details";

    @Override
    public String name() {
        return NAME;
    }
}
