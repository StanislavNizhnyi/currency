package com.spribe.currency.exchange.service.dictionary;

import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.spribe.currency.exchange.domain.entity.Currency;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DBRider
@SpringBootTest
@ActiveProfiles("test")
public class CurrencyDictionaryServiceIT {


    @Autowired
    private CurrencyDictionaryService currencyDictionaryService;

    @Test
    @ExpectedDataSet(value = "CurrencyDictionaryService-expected-add.yml", compareOperation = CompareOperation.CONTAINS)
    public void add_WhenCalledForValidCurrencyName_ThenSaveNewCurrency() {
        currencyDictionaryService.add("USD");
    }

    @Test
    public void add_WhenCalledForInvalidCurrencyName_ThenThrowException() {
        assertThatThrownBy(() -> currencyDictionaryService.add("testCurrencyName")).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "CurrencyDictionaryService-existed-getCurrencies.yml")
    public void getAllCurrencies_WhenCalled_ThenReturnAllCurrenciesInTable() {

        List<Currency> result = currencyDictionaryService.getAllCurrencies();

        assertThat(result).extracting(Currency::getCode, Currency::getName)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(1L, "TEST1"),
                        Tuple.tuple(2L, "TEST2"),
                        Tuple.tuple(3L, "TEST3")
                );
    }

    @Configuration
    @EnableAutoConfiguration
    @Import(CurrencyDictionaryServiceImpl.class)
    @EntityScan("com.spribe.currency.exchange.domain.entity")
    @EnableJpaRepositories("com.spribe.currency.exchange.repository")
    static class TestConfig {
    }

}
