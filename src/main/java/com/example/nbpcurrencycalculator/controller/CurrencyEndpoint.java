package com.example.nbpcurrencycalculator.controller;

import com.example.nbpcurrencycalculator.model.NbpCurrencyCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping(value = "/currency/calculate",
produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyEndpoint {
    @Autowired
    private NbpCurrencyCalculator nbpCurrencyCalculator;

    @GetMapping()
    ResponseEntity<?> calculateCurrency(
            @RequestParam(required = true, name = "amount") Long amount,
            @RequestParam(required = true, name = "actualCurrency") String actualCurrency,
            @RequestParam(required = true, name = "targetCurrency") String targetCurrency){
        BigDecimal bigDecimal = nbpCurrencyCalculator.calculateValue(
                BigDecimal.valueOf(amount),
                actualCurrency,
                targetCurrency
        );
        return ResponseEntity.ok(bigDecimal.setScale(2, RoundingMode.CEILING));
    }
}
