package com.example.nbpcurrencycalculator.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NbpCurrencyCalculatorTest {
    @Autowired
    private NbpCurrencyCalculator nbpCurrencyCalculator;
    @Test
    void calculateValue() {
        //given

        //when
        BigDecimal bigDecimal = nbpCurrencyCalculator.calculateValue(
                BigDecimal.valueOf(1500), "EUR", "USD");
        //then
        System.out.println(bigDecimal);
    }
}