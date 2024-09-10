package com.spribe.currency.exchange.service.log;

import java.time.LocalDateTime;

public interface CurrencyLogService {

    void addCurrencyRateLog(String source, LocalDateTime updatedAt);

}
