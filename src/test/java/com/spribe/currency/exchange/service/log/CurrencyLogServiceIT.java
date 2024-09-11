package com.spribe.currency.exchange.service.log;

import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@DBRider
@SpringBootTest
@ActiveProfiles("test")
public class CurrencyLogServiceIT {

    @Autowired
    private CurrencyLogService currencyLogService;

    @Test
    @ExpectedDataSet(value = "CurrencyLogService-expected-addCurrencyRateLog.yml", compareOperation = CompareOperation.CONTAINS)
    public void add_WhenCalledForValidCurrencyName_ThenSaveNewCurrency() {
        currencyLogService.addCurrencyRateLog("TEST_SOURCE_NAME", LocalDateTime.of(2024, 9, 11, 0, 0, 0));
    }

    @Configuration
    @EnableAutoConfiguration
    @Import(CurrencyLogServiceImpl.class)
    @EntityScan("com.spribe.currency.exchange.domain.entity")
    @EnableJpaRepositories("com.spribe.currency.exchange.repository")
    static class TestConfig {
    }

}
