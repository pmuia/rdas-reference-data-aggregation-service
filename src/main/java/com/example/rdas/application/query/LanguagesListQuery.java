package com.example.rdas.application.query;

import com.example.rdas.application.cqrs.Query;

public record LanguagesListQuery() implements Query {
    public static final String NAME = "reference.language.list";

    @Override
    public String name() {
        return NAME;
    }
}
