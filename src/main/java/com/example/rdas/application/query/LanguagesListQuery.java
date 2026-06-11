package com.example.rdas.application.query;

import com.example.rdas.application.cqrs.Query;

public record LanguagesListQuery() implements Query {
    public static final String COMMAND_NAME = "reference.language.list";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }
}
