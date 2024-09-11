package com.spribe.currency.exchange.domain.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CurrencyLayerResponseDto {

    private Boolean success;
    private Long timestamp;
    private String source;
    private Map<String, Double> quotes;

}
