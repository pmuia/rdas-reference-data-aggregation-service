package com.example.rdas.application.handler;

import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.CountryDetailsQuery;
import com.example.rdas.application.service.ReferenceDataQueryService;
import com.example.rdas.domain.model.CountryReference;
import org.springframework.stereotype.Component;

@Component
public class CountryDetailsQueryHandler implements ReferenceDataMessageHandler<CountryDetailsQuery, CountryReference> {
    private final ReferenceDataQueryService service;

    public CountryDetailsQueryHandler(ReferenceDataQueryService service) {
        this.service = service;
    }

    @Override
    public String commandName() {
        return CountryDetailsQuery.COMMAND_NAME;
    }

    @Override
    public Class<CountryDetailsQuery> messageType() {
        return CountryDetailsQuery.class;
    }

    @Override
    public CountryReference handle(CountryDetailsQuery query) {
        return service.getCountryDetails(query);
    }
}
