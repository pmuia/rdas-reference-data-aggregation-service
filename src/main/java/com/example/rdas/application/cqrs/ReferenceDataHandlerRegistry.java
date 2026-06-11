package com.example.rdas.application.cqrs;

import com.example.rdas.domain.exception.ReferenceDataException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ReferenceDataHandlerRegistry {
    private final Map<String, ReferenceDataMessageHandler<?, ?>> handlers;

    public ReferenceDataHandlerRegistry(List<ReferenceDataMessageHandler<?, ?>> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toUnmodifiableMap(
                        ReferenceDataMessageHandler::commandName,
                        Function.identity()));
    }

    public ReferenceDataMessageHandler<?, ?> get(String commandName) {
        ReferenceDataMessageHandler<?, ?> handler = handlers.get(commandName);
        if (handler == null) {
            throw new ReferenceDataException("UNSUPPORTED_SERVICE",
                    "Unsupported service: " + commandName, HttpStatus.BAD_REQUEST);
        }
        return handler;
    }
}
