package com.example.rdas.application.query;

import com.example.rdas.application.cqrs.Query;

public record ContinentsListQuery() implements Query {
    public static final String NAME = "reference.continent.list";

    @Override
    public String name() {
        return NAME;
    }
}
