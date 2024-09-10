package com.spribe.currency.exchange.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CurrencyLog {
    @Id
    @SequenceGenerator(name = "currency_log_generator", sequenceName = "currency_log_seq")
    @GeneratedValue(generator = "currency_log_generator", strategy = GenerationType.SEQUENCE)
    private final Long id;
    private final String currencySourceName;
    private final LocalDateTime updatedAt;
}
