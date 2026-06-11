package com.example.rdas.infrastructure.soap;

import com.example.rdas.domain.model.ContinentReference;
import com.example.rdas.domain.model.CountryReference;
import com.example.rdas.domain.model.CurrencyReference;
import com.example.rdas.domain.model.LanguageReference;
import java.util.List;

public interface CountryInfoSoapClient {
    List<CountryReference> fullCountryInfoAllCountries();

    CountryReference fullCountryInfo(String countryCode);

    List<ContinentReference> listOfContinentsByName();

    List<CurrencyReference> listOfCurrenciesByName();

    List<LanguageReference> listOfLanguagesByName();

    List<CountryReference> countriesUsingCurrency(String currencyCode);
}
