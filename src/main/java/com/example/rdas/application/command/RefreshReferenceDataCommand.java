package com.example.rdas.application.command;

import com.example.rdas.application.cqrs.Command;
import jakarta.validation.constraints.NotNull;

public record RefreshReferenceDataCommand(@NotNull RefreshType refreshType) implements Command {
    public static final String COMMAND_NAME = "reference.data.refresh";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    public enum RefreshType {
        FULL, PARTIAL
    }
}
