package com.spribe.currency.exchange.repository;

import com.spribe.currency.exchange.domain.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
