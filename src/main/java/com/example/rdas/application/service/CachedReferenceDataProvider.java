package com.example.rdas.application.service;

import com.example.rdas.domain.model.ContinentReference;
import com.example.rdas.domain.model.CountryReference;
import com.example.rdas.domain.model.CurrencyReference;
import com.example.rdas.domain.model.LanguageReference;
import com.example.rdas.infrastructure.soap.CountryInfoSoapClient;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedReferenceDataProvider {
    private final CountryInfoSoapClient soapClient;

    public CachedReferenceDataProvider(CountryInfoSoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @Cacheable(cacheNames = "referenceData", key = "'countries:all'")
    public List<CountryReference> getAllCountries() {
        return soapClient.fullCountryInfoAllCountries();
    }

    @Cacheable(cacheNames = "referenceData", key = "'countries:' + #countryCode.toUpperCase()")
    public CountryReference getCountryDetails(String countryCode) {
        return soapClient.fullCountryInfo(countryCode);
    }

    @Cacheable(cacheNames = "referenceData", key = "'countries:currency:' + #currencyCode.toUpperCase()")
    public List<CountryReference> getCountriesUsingCurrency(String currencyCode) {
        return soapClient.countriesUsingCurrency(currencyCode);
    }

    @Cacheable(cacheNames = "referenceData", key = "'continents:all'")
    public List<ContinentReference> getContinents() {
        return soapClient.listOfContinentsByName();
    }

    @Cacheable(cacheNames = "referenceData", key = "'currencies:all'")
    public List<CurrencyReference> getCurrencies() {
        return soapClient.listOfCurrenciesByName();
    }

    @Cacheable(cacheNames = "referenceData", key = "'languages:all'")
    public List<LanguageReference> getLanguages() {
        return soapClient.listOfLanguagesByName();
    }

    @CacheEvict(cacheNames = "referenceData", allEntries = true)
    public void clearCaches() {
    }
}
