package com.spribe.currency.exchange.service.dictionary;

import com.spribe.currency.exchange.domain.entity.Currency;

import java.util.List;

public interface CurrencyDictionaryService {

    void add(String name);

    List<Currency> getAllCurrencies();

}
