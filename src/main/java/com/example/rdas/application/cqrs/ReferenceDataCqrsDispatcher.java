package com.example.rdas.application.cqrs;

import com.example.rdas.api.request.ReferenceDataRequest;
import com.example.rdas.api.response.ReferenceDataResponse;
import com.example.rdas.domain.exception.ReferenceDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ReferenceDataCqrsDispatcher {
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final ReferenceDataHandlerRegistry handlerRegistry;

    public ReferenceDataCqrsDispatcher(
            ObjectMapper objectMapper,
            Validator validator,
            ReferenceDataHandlerRegistry handlerRegistry) {
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.handlerRegistry = handlerRegistry;
    }

    public ReferenceDataResponse<?> dispatch(ReferenceDataRequest request) {
        ReferenceDataMessageHandler<?, ?> handler = handlerRegistry.get(request.service());
        ReferenceDataMessage message = convert(request, handler.messageType());
        if (!request.service().equals(message.commandName())) {
            throw new ReferenceDataException("UNSUPPORTED_SERVICE",
                    "Service does not match command name: " + request.service(), HttpStatus.BAD_REQUEST);
        }
        return ReferenceDataResponse.success(request.service(), invoke(handler, message));
    }

    private <M extends ReferenceDataMessage> M convert(ReferenceDataRequest request, Class<M> messageType) {
        M message;
        try {
            message = objectMapper.convertValue(request.data(), messageType);
        } catch (IllegalArgumentException exception) {
            throw new ReferenceDataException("VALIDATION_ERROR", "Invalid request data", HttpStatus.BAD_REQUEST);
        }
        validate(message);
        return message;
    }

    private <M extends ReferenceDataMessage> void validate(M message) {
        Set<ConstraintViolation<M>> violations = validator.validate(message);
        if (!violations.isEmpty()) {
            String validationMessage = violations.stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new ReferenceDataException("VALIDATION_ERROR", validationMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("unchecked")
    private <M extends ReferenceDataMessage, R> R invoke(
            ReferenceDataMessageHandler<?, ?> handler,
            ReferenceDataMessage message) {
        return ((ReferenceDataMessageHandler<M, R>) handler).handle((M) message);
    }
}
