package com.primedice.app.mapper;

import com.primedice.common.entity.Currency;

import java.util.List;

public interface CurrencyMapper {
    List<Currency> findByUserId(Long id);
    int createCurrency(Currency currency);
}
