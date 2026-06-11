package com.example.rdas.api.controller;

import com.example.rdas.api.request.ReferenceDataRequest;
import com.example.rdas.api.response.ReferenceDataResponse;
import com.example.rdas.application.cqrs.ReferenceDataCqrsDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reference-data")
public class ReferenceDataController {
    private final ReferenceDataCqrsDispatcher dispatcher;

    public ReferenceDataController(ReferenceDataCqrsDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @PostMapping
    public ResponseEntity<ReferenceDataResponse<?>> process(
            @Valid @RequestBody ReferenceDataRequest request, HttpServletRequest servletRequest) {
        servletRequest.setAttribute("service", request.service());
        return ResponseEntity.ok(dispatcher.dispatch(request));
    }
}
