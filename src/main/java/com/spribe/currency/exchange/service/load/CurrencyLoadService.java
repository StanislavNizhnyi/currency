package com.spribe.currency.exchange.service.load;

import com.spribe.currency.exchange.domain.dto.CurrencyLayerResponseDto;

public interface CurrencyLoadService {

    CurrencyLayerResponseDto uploadCurrencies(String source);

}
