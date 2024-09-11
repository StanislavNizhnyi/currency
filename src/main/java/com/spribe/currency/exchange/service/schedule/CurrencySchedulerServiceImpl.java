package com.spribe.currency.exchange.service.schedule;


import com.spribe.currency.exchange.domain.dto.CurrencyLayerResponseDto;
import com.spribe.currency.exchange.domain.entity.Currency;
import com.spribe.currency.exchange.service.dictionary.CurrencyDictionaryService;
import com.spribe.currency.exchange.service.load.CurrencyLoadService;
import com.spribe.currency.exchange.service.log.CurrencyLogService;
import com.spribe.currency.exchange.service.storage.CurrencyStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencySchedulerServiceImpl implements CurrencySchedulerService {

    private final CurrencyLoadService currencyLoadService;
    private final CurrencyDictionaryService currencyDictionaryService;
    private final CurrencyStorageService currencyStorageService;
    private final CurrencyLogService currencyLogService;

    @Override
    @Scheduled(cron = "0 0 */1 * * ?")
    public void schedule() {
        List<Currency> allCurrencies = currencyDictionaryService.getAllCurrencies();
        if (allCurrencies.isEmpty()) {
            return;
        }
        allCurrencies.stream()
                .map(currency -> currencyLoadService.uploadCurrencies(currency.getName()))
                .forEach(this::logAndSave);
    }

    private void logAndSave(CurrencyLayerResponseDto currencyLayerResponseDto) {
        String responseSource = currencyLayerResponseDto.getSource();
        Optional.of(currencyLayerResponseDto)
                .filter(CurrencyLayerResponseDto::getSuccess)
                .map(CurrencyLayerResponseDto::getQuotes)
                .map(quote -> getRates(quote, responseSource))
                .ifPresent(rates -> update(currencyLayerResponseDto, rates));
    }

    private void update(CurrencyLayerResponseDto currencyLayerResponseDto, Map<String, Double> rates) {
        String source = currencyLayerResponseDto.getSource();
        currencyStorageService.update(source, rates);
        LocalDateTime updatedAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(currencyLayerResponseDto.getTimestamp()), ZoneId.systemDefault());
        currencyLogService.addCurrencyRateLog(source, updatedAt);
    }

    private Map<String, Double> getRates(Map<String, Double> quotes, String responseSource) {
        return quotes.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey().replace(responseSource, ""), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
