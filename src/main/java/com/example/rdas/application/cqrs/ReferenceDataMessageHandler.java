package com.example.rdas.application.cqrs;

public interface ReferenceDataMessageHandler<M extends ReferenceDataMessage, R> {
    String commandName();

    Class<M> messageType();

    R handle(M message);
}
