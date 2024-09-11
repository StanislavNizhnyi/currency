package com.spribe.currency.exchange.controller;

import com.spribe.currency.exchange.domain.entity.Currency;
import com.spribe.currency.exchange.service.dictionary.CurrencyDictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CurrencyController {


    private CurrencyDictionaryService currencyDictionaryService;

    @GetMapping(path = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCurrencies() {
        return currencyDictionaryService.getAllCurrencies().stream()
                .map(Currency::getName)
                .toList();
    }

    @PutMapping(path = "/currencies/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCurrency(@PathVariable String code) {
        currencyDictionaryService.add(code);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
