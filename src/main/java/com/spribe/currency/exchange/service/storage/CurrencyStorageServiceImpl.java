package com.spribe.currency.exchange.service.storage;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CurrencyStorageServiceImpl implements CurrencyStorageService {

    private final Map<String, Map<String, Double>> ratesStorage = new ConcurrentHashMap<>();

    @Override
    public void update(String source, Map<String, Double> rates) {
        ratesStorage.put(source, rates);
    }

    @Override
    public Map<String, Double> getRates(String source) {
        return ratesStorage.getOrDefault(source, Collections.emptyMap());
    }
}
