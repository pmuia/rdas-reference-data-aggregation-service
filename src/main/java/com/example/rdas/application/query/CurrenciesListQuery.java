package com.example.rdas.application.query;

import com.example.rdas.application.cqrs.Query;

public record CurrenciesListQuery() implements Query {
    public static final String NAME = "reference.currency.list";

    @Override
    public String name() {
        return NAME;
    }
}
