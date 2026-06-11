package com.example.rdas.application.query;

import com.example.rdas.application.cqrs.Query;

public record CurrenciesListQuery() implements Query {
    public static final String COMMAND_NAME = "reference.currency.list";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }
}
