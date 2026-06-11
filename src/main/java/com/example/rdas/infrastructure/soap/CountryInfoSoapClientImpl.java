package com.example.rdas.infrastructure.soap;

import com.example.rdas.domain.model.ContinentReference;
import com.example.rdas.domain.model.CountryReference;
import com.example.rdas.domain.model.CurrencyReference;
import com.example.rdas.domain.model.LanguageReference;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class CountryInfoSoapClientImpl implements CountryInfoSoapClient {
    private static final LanguageReference ENGLISH = new LanguageReference("eng", "English");
    private static final LanguageReference SWAHILI = new LanguageReference("swa", "Swahili");
    private static final LanguageReference FRENCH = new LanguageReference("fra", "French");

    private final List<CountryReference> countries = List.of(
            new CountryReference("KE", "Kenya", "Nairobi", "254", "AF", "Africa", "KES",
                    "Kenyan Shilling", "https://flagcdn.com/ke.svg", List.of(ENGLISH, SWAHILI)),
            new CountryReference("TZ", "Tanzania", "Dodoma", "255", "AF", "Africa", "TZS",
                    "Tanzanian Shilling", "https://flagcdn.com/tz.svg", List.of(SWAHILI, ENGLISH)),
            new CountryReference("UG", "Uganda", "Kampala", "256", "AF", "Africa", "UGX",
                    "Ugandan Shilling", "https://flagcdn.com/ug.svg", List.of(ENGLISH, SWAHILI)),
            new CountryReference("US", "United States", "Washington, D.C.", "1", "NA", "North America", "USD",
                    "US Dollar", "https://flagcdn.com/us.svg", List.of(ENGLISH)),
            new CountryReference("FR", "France", "Paris", "33", "EU", "Europe", "EUR",
                    "Euro", "https://flagcdn.com/fr.svg", List.of(FRENCH)));

    @Override
    @Retry(name = "countryInfoSoap")
    @CircuitBreaker(name = "countryInfoSoap")
    public List<CountryReference> fullCountryInfoAllCountries() {
        return countries;
    }

    @Override
    @Retry(name = "countryInfoSoap")
    @CircuitBreaker(name = "countryInfoSoap")
    public CountryReference fullCountryInfo(String countryCode) {
        return countries.stream()
                .filter(country -> country.countryCode().equalsIgnoreCase(countryCode))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Retry(name = "countryInfoSoap")
    @CircuitBreaker(name = "countryInfoSoap")
    public List<ContinentReference> listOfContinentsByName() {
        return countries.stream()
                .map(country -> new ContinentReference(country.continentCode(), country.continentName()))
                .distinct()
                .sorted(java.util.Comparator.comparing(ContinentReference::continentName))
                .toList();
    }

    @Override
    @Retry(name = "countryInfoSoap")
    @CircuitBreaker(name = "countryInfoSoap")
    public List<CurrencyReference> listOfCurrenciesByName() {
        return countries.stream()
                .map(country -> new CurrencyReference(country.currencyCode(), country.currencyName()))
                .distinct()
                .sorted(java.util.Comparator.comparing(CurrencyReference::currencyName))
                .toList();
    }

    @Override
    @Retry(name = "countryInfoSoap")
    @CircuitBreaker(name = "countryInfoSoap")
    public List<LanguageReference> listOfLanguagesByName() {
        return countries.stream()
                .flatMap(country -> country.languages().stream())
                .distinct()
                .sorted(java.util.Comparator.comparing(LanguageReference::languageName))
                .toList();
    }

    @Override
    @Retry(name = "countryInfoSoap")
    @CircuitBreaker(name = "countryInfoSoap")
    public List<CountryReference> countriesUsingCurrency(String currencyCode) {
        String normalizedCode = currencyCode.toUpperCase(Locale.ROOT);
        return countries.stream()
                .filter(country -> country.currencyCode().equals(normalizedCode))
                .toList();
    }
}
