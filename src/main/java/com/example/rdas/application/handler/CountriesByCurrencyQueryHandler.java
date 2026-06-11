package com.example.rdas.application.handler;

import com.example.rdas.api.response.PagedCountriesResponse;
import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.CountriesByCurrencyQuery;
import com.example.rdas.application.service.ReferenceDataQueryService;
import org.springframework.stereotype.Component;

@Component
public class CountriesByCurrencyQueryHandler implements ReferenceDataMessageHandler<CountriesByCurrencyQuery, PagedCountriesResponse> {
    private final ReferenceDataQueryService service;

    public CountriesByCurrencyQueryHandler(ReferenceDataQueryService service) {
        this.service = service;
    }

    @Override
    public String commandName() {
        return CountriesByCurrencyQuery.COMMAND_NAME;
    }

    @Override
    public Class<CountriesByCurrencyQuery> messageType() {
        return CountriesByCurrencyQuery.class;
    }

    @Override
    public PagedCountriesResponse handle(CountriesByCurrencyQuery query) {
        return service.getCountriesByCurrency(query);
    }
}
