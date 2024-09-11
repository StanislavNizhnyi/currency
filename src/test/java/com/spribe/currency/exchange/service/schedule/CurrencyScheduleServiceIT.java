package com.spribe.currency.exchange.service.schedule;

import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.spribe.currency.exchange.domain.dto.CurrencyLayerResponseDto;
import com.spribe.currency.exchange.service.dictionary.CurrencyDictionaryServiceImpl;
import com.spribe.currency.exchange.service.load.CurrencyLoadService;
import com.spribe.currency.exchange.service.log.CurrencyLogServiceImpl;
import com.spribe.currency.exchange.service.storage.CurrencyStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import static org.mockito.Mockito.*;

@DBRider
@SpringBootTest
@ActiveProfiles("test")
public class CurrencyScheduleServiceIT {

    @MockBean
    private CurrencyLoadService currencyLoadService;
    @MockBean
    private CurrencyStorageService currencyStorageService;

    @Autowired
    private CurrencySchedulerService currencySchedulerService;

    @Test
    @DataSet(cleanAfter = true, cleanBefore = true, value = "CurrencyDictionaryService-existed-getCurrencies.yml")
    @ExpectedDataSet(value = "CurrencyScheduleService-expected-schedule.yml", compareOperation = CompareOperation.CONTAINS)
    public void schedule_WhenCalled_ThenScheduleAndUpdateLogOnlyForSuccessCurrencies() {
        String source = "SOURCE";
        Map<String, Double> rates = Map.of("TEST", 25.43D);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 9, 11, 0, 0, 0);
        CurrencyLayerResponseDto nonSuccessResponse = mock(CurrencyLayerResponseDto.class);
        when(nonSuccessResponse.getSuccess()).thenReturn(false);
        CurrencyLayerResponseDto successResponse = mock(CurrencyLayerResponseDto.class);
        when(successResponse.getSuccess()).thenReturn(true);
        when(successResponse.getSource()).thenReturn(source);
        when(successResponse.getQuotes()).thenReturn(rates);
        when(successResponse.getTimestamp()).thenReturn(updatedAt.atZone(ZoneId.systemDefault()).toEpochSecond());
        when(currencyLoadService.uploadCurrencies("TEST1")).thenReturn(nonSuccessResponse);
        when(currencyLoadService.uploadCurrencies("TEST2")).thenReturn(nonSuccessResponse);
        when(currencyLoadService.uploadCurrencies("TEST3")).thenReturn(successResponse);

        currencySchedulerService.schedule();

        verify(currencyStorageService).update(eq(source), anyMap());
        verifyNoMoreInteractions(currencyStorageService);
    }

    @Configuration
    @EnableAutoConfiguration
    @Import({CurrencySchedulerServiceImpl.class, CurrencyLogServiceImpl.class, CurrencyDictionaryServiceImpl.class})
    @EntityScan("com.spribe.currency.exchange.domain.entity")
    @EnableJpaRepositories("com.spribe.currency.exchange.repository")
    static class TestConfig {
    }

}
