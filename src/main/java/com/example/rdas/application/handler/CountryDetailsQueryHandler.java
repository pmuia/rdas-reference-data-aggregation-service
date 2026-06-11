package com.example.rdas.application.handler;

import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.CountryDetailsQuery;
import com.example.rdas.application.service.ReferenceDataApplicationService;
import com.example.rdas.domain.model.CountryReference;
import org.springframework.stereotype.Component;

@Component
public class CountryDetailsQueryHandler implements ReferenceDataMessageHandler<CountryDetailsQuery, CountryReference> {
    private final ReferenceDataApplicationService service;

    public CountryDetailsQueryHandler(ReferenceDataApplicationService service) {
        this.service = service;
    }

    @Override
    public String messageName() {
        return CountryDetailsQuery.NAME;
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
