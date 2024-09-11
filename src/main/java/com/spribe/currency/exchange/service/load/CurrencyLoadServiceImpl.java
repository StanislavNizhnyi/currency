package com.spribe.currency.exchange.service.load;

import com.spribe.currency.exchange.domain.dto.CurrencyLayerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyLoadServiceImpl implements CurrencyLoadService {

    @Value("${currency.exchange-rates.server}")
    private String server;
    @Value("${currency.exchange-rates.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CurrencyLayerResponseDto uploadCurrencies(String source) {
        String requestPath = server + "?access_key={accessKey}&source={source}&format=1";
        Map<String, Object> uriVariables = Map.of("accessKey", apiKey, "source", source);
        return restTemplate.getForObject(requestPath, CurrencyLayerResponseDto.class, uriVariables);
    }

}
