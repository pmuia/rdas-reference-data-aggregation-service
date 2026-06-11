package com.example.rdas.application.handler;

import com.example.rdas.api.response.RefreshResponse;
import com.example.rdas.application.command.RefreshReferenceDataCommand;
import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.service.ReferenceDataApplicationService;
import org.springframework.stereotype.Component;

@Component
public class RefreshReferenceDataCommandHandler implements ReferenceDataMessageHandler<RefreshReferenceDataCommand, RefreshResponse> {
    private final ReferenceDataApplicationService service;

    public RefreshReferenceDataCommandHandler(ReferenceDataApplicationService service) {
        this.service = service;
    }

    @Override
    public String messageName() {
        return RefreshReferenceDataCommand.NAME;
    }

    @Override
    public Class<RefreshReferenceDataCommand> messageType() {
        return RefreshReferenceDataCommand.class;
    }

    @Override
    public RefreshResponse handle(RefreshReferenceDataCommand command) {
        return service.refresh(command);
    }
}
