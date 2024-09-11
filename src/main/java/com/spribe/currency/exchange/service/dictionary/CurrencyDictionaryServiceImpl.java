package com.spribe.currency.exchange.service.dictionary;

import com.spribe.currency.exchange.domain.entity.Currency;
import com.spribe.currency.exchange.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyDictionaryServiceImpl implements CurrencyDictionaryService {

    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional
    public void add(String name) {
        java.util.Currency savedCurrency = java.util.Currency.getAvailableCurrencies().stream()
                .filter(currency -> currency.getCurrencyCode().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
        long numericCode = Integer.valueOf(savedCurrency.getNumericCode()).longValue();
        Currency newCurrency = Currency.builder()
                .code(numericCode)
                .name(savedCurrency.getCurrencyCode())
                .build();
        currencyRepository.save(newCurrency);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
