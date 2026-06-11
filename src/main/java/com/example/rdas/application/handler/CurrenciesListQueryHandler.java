package com.example.rdas.application.handler;

import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.CurrenciesListQuery;
import com.example.rdas.application.service.ReferenceDataQueryService;
import com.example.rdas.domain.model.CurrencyReference;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesListQueryHandler implements ReferenceDataMessageHandler<CurrenciesListQuery, List<CurrencyReference>> {
    private final ReferenceDataQueryService service;

    public CurrenciesListQueryHandler(ReferenceDataQueryService service) {
        this.service = service;
    }

    @Override
    public String commandName() {
        return CurrenciesListQuery.COMMAND_NAME;
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
