package com.spribe.currency.exchange.service.log;

import com.spribe.currency.exchange.domain.entity.CurrencyLog;
import com.spribe.currency.exchange.repository.CurrencyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CurrencyLogServiceImpl implements CurrencyLogService {

    private final CurrencyLogRepository currencyLogRepository;

    @Override
    @Transactional
    public void addCurrencyRateLog(String source, LocalDateTime updatedAt) {
        CurrencyLog currencyLog = CurrencyLog.builder()
                .currencySourceName(source)
                .updatedAt(updatedAt)
                .build();
        currencyLogRepository.save(currencyLog);
    }
}
