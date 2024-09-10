package com.spribe.currency.exchange.repository;

import com.spribe.currency.exchange.domain.entity.CurrencyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyLogRepository extends JpaRepository<CurrencyLog, Long> {
}
