package com.example.rdas.application.handler;

import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.ContinentsListQuery;
import com.example.rdas.application.service.ReferenceDataQueryService;
import com.example.rdas.domain.model.ContinentReference;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ContinentsListQueryHandler implements ReferenceDataMessageHandler<ContinentsListQuery, List<ContinentReference>> {
    private final ReferenceDataQueryService service;

    public ContinentsListQueryHandler(ReferenceDataQueryService service) {
        this.service = service;
    }

    @Override
    public String commandName() {
        return ContinentsListQuery.COMMAND_NAME;
    }

    @Override
    public Class<ContinentsListQuery> messageType() {
        return ContinentsListQuery.class;
    }

    @Override
    public List<ContinentReference> handle(ContinentsListQuery query) {
        return service.getContinents();
    }
}
