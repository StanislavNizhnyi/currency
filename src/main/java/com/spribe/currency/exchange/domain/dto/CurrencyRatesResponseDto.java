package com.spribe.currency.exchange.domain.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;


@Getter
@SuperBuilder
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CurrencyRatesResponseDto {

    private final String source;
    private final Map<String, Double> rates;

}
