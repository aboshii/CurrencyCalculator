package com.example.nbpcurrencycalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NbpCurrency {
    private CurrencyEnum currencyEnum;
    private String name;
    private String bid;
    private String ask;
}
