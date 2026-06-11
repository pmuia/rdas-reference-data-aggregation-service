package com.example.rdas.application.handler;

import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.CurrenciesListQuery;
import com.example.rdas.application.service.ReferenceDataApplicationService;
import com.example.rdas.domain.model.CurrencyReference;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesListQueryHandler implements ReferenceDataMessageHandler<CurrenciesListQuery, List<CurrencyReference>> {
    private final ReferenceDataApplicationService service;

    public CurrenciesListQueryHandler(ReferenceDataApplicationService service) {
        this.service = service;
    }

    @Override
    public String messageName() {
        return CurrenciesListQuery.NAME;
    }

    @Override
    public Class<CurrenciesListQuery> messageType() {
        return CurrenciesListQuery.class;
    }

    @Override
    public List<CurrencyReference> handle(CurrenciesListQuery query) {
        return service.getCurrencies();
    }
}
