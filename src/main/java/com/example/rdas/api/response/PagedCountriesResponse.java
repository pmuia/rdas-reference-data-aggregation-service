package com.example.rdas.api.response;

import com.example.rdas.domain.model.CountryReference;
import java.util.List;

public record PagedCountriesResponse(
        int page,
        int size,
        long totalElements,
        int totalPages,
        List<CountryReference> countries) {
}
