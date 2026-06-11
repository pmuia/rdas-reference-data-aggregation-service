package com.example.rdas.application.cqrs;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.rdas.application.command.RefreshReferenceDataCommand;
import com.example.rdas.application.query.ContinentsListQuery;
import com.example.rdas.application.query.CountriesByCurrencyQuery;
import com.example.rdas.application.query.CountryDetailsQuery;
import com.example.rdas.application.query.CountrySearchQuery;
import com.example.rdas.application.query.CurrenciesListQuery;
import com.example.rdas.application.query.LanguagesListQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class ReferenceDataHandlerRegistryTest {
    @Autowired
    private ReferenceDataHandlerRegistry registry;

    @Test
    void mapsServiceNamesToCqrsMessageTypes() {
        assertThat(registry.get(CountrySearchQuery.COMMAND_NAME).messageType()).isEqualTo(CountrySearchQuery.class);
        assertThat(registry.get(CountryDetailsQuery.COMMAND_NAME).messageType()).isEqualTo(CountryDetailsQuery.class);
        assertThat(registry.get(CountriesByCurrencyQuery.COMMAND_NAME).messageType()).isEqualTo(CountriesByCurrencyQuery.class);
        assertThat(registry.get(ContinentsListQuery.COMMAND_NAME).messageType()).isEqualTo(ContinentsListQuery.class);
        assertThat(registry.get(CurrenciesListQuery.COMMAND_NAME).messageType()).isEqualTo(CurrenciesListQuery.class);
        assertThat(registry.get(LanguagesListQuery.COMMAND_NAME).messageType()).isEqualTo(LanguagesListQuery.class);
        assertThat(registry.get(RefreshReferenceDataCommand.COMMAND_NAME).messageType())
                .isEqualTo(RefreshReferenceDataCommand.class);
    }

    @Test
    void exposesServiceNamesAsCommandNames() {
        assertThat(registry.get(CountrySearchQuery.COMMAND_NAME).commandName()).isEqualTo("reference.country.search");
        assertThat(registry.get(CountryDetailsQuery.COMMAND_NAME).commandName()).isEqualTo("reference.country.details");
        assertThat(registry.get(RefreshReferenceDataCommand.COMMAND_NAME).commandName()).isEqualTo("reference.data.refresh");
    }

    @Test
    void separatesQueriesFromCommands() {
        assertThat(Query.class).isAssignableFrom(registry.get(CountrySearchQuery.COMMAND_NAME).messageType());
        assertThat(Command.class).isAssignableFrom(registry.get(RefreshReferenceDataCommand.COMMAND_NAME).messageType());
    }
}
