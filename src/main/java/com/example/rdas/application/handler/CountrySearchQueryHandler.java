package com.example.rdas.application.handler;

import com.example.rdas.api.response.PagedCountriesResponse;
import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.CountrySearchQuery;
import com.example.rdas.application.service.ReferenceDataApplicationService;
import org.springframework.stereotype.Component;

@Component
public class CountrySearchQueryHandler implements ReferenceDataMessageHandler<CountrySearchQuery, PagedCountriesResponse> {
    private final ReferenceDataApplicationService service;

    public CountrySearchQueryHandler(ReferenceDataApplicationService service) {
        this.service = service;
    }

    @Override
    public String messageName() {
        return CountrySearchQuery.NAME;
    }

    @Override
    public Class<CountrySearchQuery> messageType() {
        return CountrySearchQuery.class;
    }

    @Override
    public PagedCountriesResponse handle(CountrySearchQuery query) {
        return service.searchCountries(query);
    }
}
