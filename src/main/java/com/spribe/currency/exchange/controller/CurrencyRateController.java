package com.spribe.currency.exchange.controller;

import com.spribe.currency.exchange.domain.dto.CurrencyRatesResponseDto;
import com.spribe.currency.exchange.service.storage.CurrencyStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CurrencyRateController {

    private CurrencyStorageService currencyStorageService;

    @GetMapping(path = "/currency-rates", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyRatesResponseDto getCurrencyRates(@RequestParam String source) {
        Map<String, Double> rates = currencyStorageService.getRates(source);
        return CurrencyRatesResponseDto.builder()
                .source(source)
                .rates(rates)
                .build();
    }

}
