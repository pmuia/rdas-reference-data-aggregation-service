package com.example.rdas.application.service;

import com.example.rdas.api.response.RefreshResponse;
import com.example.rdas.application.command.RefreshReferenceDataCommand;
import java.time.Instant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReferenceDataCommandService {
    private final CachedReferenceDataProvider dataProvider;

    public ReferenceDataCommandService(CachedReferenceDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @PreAuthorize("hasAuthority('reference-data:refresh')")
    public RefreshResponse refresh(RefreshReferenceDataCommand command) {
        dataProvider.clearCaches();
        if (command.refreshType() == RefreshReferenceDataCommand.RefreshType.FULL) {
            dataProvider.getAllCountries();
            dataProvider.getContinents();
            dataProvider.getCurrencies();
            dataProvider.getLanguages();
        }
        return new RefreshResponse(command.refreshType().name(), "COMPLETED", Instant.now());
    }
}
