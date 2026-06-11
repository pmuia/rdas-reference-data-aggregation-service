package com.example.rdas.application.command;

import com.example.rdas.application.cqrs.Command;
import jakarta.validation.constraints.NotNull;

public record RefreshReferenceDataCommand(@NotNull RefreshType refreshType) implements Command {
    public static final String NAME = "reference.data.refresh";

    @Override
    public String name() {
        return NAME;
    }

    public enum RefreshType {
        FULL, PARTIAL
    }
}
