package com.example.rdas.domain.model;

import java.util.List;

public record CountryReference(
        String countryCode,
        String countryName,
        String capitalCity,
        String phoneCode,
        String continentCode,
        String continentName,
        String currencyCode,
        String currencyName,
        String flagUrl,
        List<LanguageReference> languages) implements java.io.Serializable {
}
