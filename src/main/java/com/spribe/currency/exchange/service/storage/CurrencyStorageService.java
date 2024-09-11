package com.spribe.currency.exchange.service.storage;

import java.util.Map;

public interface CurrencyStorageService {

    void update(String source, Map<String, Double> rates);

    Map<String, Double> getRates(String source);
}
