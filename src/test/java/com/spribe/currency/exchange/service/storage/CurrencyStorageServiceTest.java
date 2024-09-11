package com.spribe.currency.exchange.service.storage;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyStorageServiceTest {

    private final CurrencyStorageServiceImpl currencyStorageService = new CurrencyStorageServiceImpl();

    @Test
    void updateAndGetRates_WhenCalled_ThenStoreReturnCorrectRates() {
        String source = "TEST";
        Map<String, Double> rates = Map.of("", 123.4D);

        currencyStorageService.update(source, rates);

        assertThat(currencyStorageService.getRates(source)).isEqualTo(rates);
    }

    @Test
    void getRates_WhenCalledForNonExistedSource_ThenReturnEmptyMap() {
        Map<String, Double> rates = currencyStorageService.getRates("NonPresentSource");

        assertThat(rates).isEmpty();
    }
}
