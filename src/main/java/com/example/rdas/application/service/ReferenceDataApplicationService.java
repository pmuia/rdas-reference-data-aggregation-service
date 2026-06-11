package com.example.rdas.application.service;

import com.example.rdas.api.request.PageRequestData;
import com.example.rdas.api.response.PagedCountriesResponse;
import com.example.rdas.api.response.RefreshResponse;
import com.example.rdas.application.command.RefreshReferenceDataCommand;
import com.example.rdas.application.query.CountriesByCurrencyQuery;
import com.example.rdas.application.query.CountryDetailsQuery;
import com.example.rdas.application.query.CountrySearchQuery;
import com.example.rdas.domain.exception.ReferenceDataException;
import com.example.rdas.domain.model.ContinentReference;
import com.example.rdas.domain.model.CountryReference;
import com.example.rdas.domain.model.CurrencyReference;
import com.example.rdas.domain.model.LanguageReference;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReferenceDataApplicationService {
    private final CachedReferenceDataProvider dataProvider;

    public ReferenceDataApplicationService(CachedReferenceDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @PreAuthorize("hasAuthority('reference-data:read')")
    public PagedCountriesResponse searchCountries(CountrySearchQuery query) {
        List<CountryReference> countries = dataProvider.getAllCountries().stream()
                .filter(country -> contains(country.countryName(), query.countryName()))
                .filter(country -> equalsCode(country.continentCode(), query.continentCode()))
                .filter(country -> equalsCode(country.currencyCode(), query.currencyCode()))
                .filter(country -> query.languageCode() == null || country.languages().stream()
                        .anyMatch(language -> equalsCode(language.languageCode(), query.languageCode())))
                .toList();
        return page(countries, query.pageRequest());
    }

    @PreAuthorize("hasAuthority('reference-data:read')")
    public CountryReference getCountryDetails(CountryDetailsQuery query) {
        CountryReference country = dataProvider.getCountryDetails(query.countryCode());
        if (country == null) {
            throw new ReferenceDataException("COUNTRY_NOT_FOUND",
                    "Country not found: " + query.countryCode(), HttpStatus.NOT_FOUND);
        }
        return country;
    }

    @PreAuthorize("hasAuthority('reference-data:read')")
    public PagedCountriesResponse getCountriesByCurrency(CountriesByCurrencyQuery query) {
        return page(dataProvider.getCountriesUsingCurrency(query.currencyCode()), query.pageRequest());
    }

    @PreAuthorize("hasAuthority('reference-data:read')")
    public List<ContinentReference> getContinents() {
        return dataProvider.getContinents();
    }

    @PreAuthorize("hasAuthority('reference-data:read')")
    public List<CurrencyReference> getCurrencies() {
        return dataProvider.getCurrencies();
    }

    @PreAuthorize("hasAuthority('reference-data:read')")
    public List<LanguageReference> getLanguages() {
        return dataProvider.getLanguages();
    }

    @PreAuthorize("hasAuthority('reference-data:refresh')")
    public RefreshResponse refresh(RefreshReferenceDataCommand command) {
        dataProvider.clearCaches();
        if (command.refreshType() == RefreshReferenceDataCommand.RefreshType.FULL) {
            dataProvider.getAllCountries();
            dataProvider.getContinents();
            dataProvider.getCurrencies();
            dataProvider.getLanguages();
        }
        return new RefreshResponse(command.refreshType().name(), "COMPLETED", Instant.now());
    }

    private PagedCountriesResponse page(List<CountryReference> countries, PageRequestData request) {
        List<CountryReference> sorted = countries.stream().sorted(comparator(request)).toList();
        int fromIndex = (int) Math.min((long) request.resolvedPage() * request.resolvedSize(), sorted.size());
        int toIndex = Math.min(fromIndex + request.resolvedSize(), sorted.size());
        int totalPages = (int) Math.ceil((double) sorted.size() / request.resolvedSize());
        return new PagedCountriesResponse(request.resolvedPage(), request.resolvedSize(), sorted.size(), totalPages,
                sorted.subList(fromIndex, toIndex));
    }

    private Comparator<CountryReference> comparator(PageRequestData request) {
        Function<CountryReference, String> field = switch (request.resolvedSortBy()) {
            case "countryCode" -> CountryReference::countryCode;
            case "continentName" -> CountryReference::continentName;
            case "currencyCode" -> CountryReference::currencyCode;
            default -> CountryReference::countryName;
        };
        Comparator<CountryReference> comparator = Comparator.comparing(field, String.CASE_INSENSITIVE_ORDER);
        return "DESC".equals(request.resolvedSortDirection()) ? comparator.reversed() : comparator;
    }

    private boolean contains(String value, String query) {
        return query == null || value.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT));
    }

    private boolean equalsCode(String value, String query) {
        return query == null || value.equalsIgnoreCase(query);
    }
}
