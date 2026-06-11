package com.example.rdas.application.handler;

import com.example.rdas.api.response.RefreshResponse;
import com.example.rdas.application.command.RefreshReferenceDataCommand;
import com.example.rdas.application.cqrs.ReferenceDataMessageHandler;
import com.example.rdas.application.service.ReferenceDataCommandService;
import org.springframework.stereotype.Component;

@Component
public class RefreshReferenceDataCommandHandler implements ReferenceDataMessageHandler<RefreshReferenceDataCommand, RefreshResponse> {
    private final ReferenceDataCommandService service;

    public RefreshReferenceDataCommandHandler(ReferenceDataCommandService service) {
        this.service = service;
    }

    @Override
    public String commandName() {
        return RefreshReferenceDataCommand.COMMAND_NAME;
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
