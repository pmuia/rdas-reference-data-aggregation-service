package com.example.rdas.api.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record PageRequestData(
        @Min(0) Integer page,
        @Min(1) @Max(100) Integer size,
        @Pattern(regexp = "countryName|countryCode|continentName|currencyCode") String sortBy,
        @Pattern(regexp = "(?i)ASC|DESC") String sortDirection) {

    public int resolvedPage() {
        return page == null ? 0 : page;
    }

    public int resolvedSize() {
        return size == null ? 20 : size;
    }

    public String resolvedSortBy() {
        return sortBy == null ? "countryName" : sortBy;
    }

    public String resolvedSortDirection() {
        return sortDirection == null ? "ASC" : sortDirection.toUpperCase();
    }
}
