package com.example.rdas.application.handler;

import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.query.LanguagesListQuery;
import com.example.rdas.application.service.ReferenceDataQueryService;
import com.example.rdas.domain.model.LanguageReference;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LanguagesListQueryHandler implements ReferenceDataMessageHandler<LanguagesListQuery, List<LanguageReference>> {
    private final ReferenceDataQueryService service;

    public LanguagesListQueryHandler(ReferenceDataQueryService service) {
        this.service = service;
    }

    @Override
    public String commandName() {
        return LanguagesListQuery.COMMAND_NAME;
    }

    @Override
    public Class<LanguagesListQuery> messageType() {
        return LanguagesListQuery.class;
    }

    @Override
    public List<LanguageReference> handle(LanguagesListQuery query) {
        return service.getLanguages();
    }
}
