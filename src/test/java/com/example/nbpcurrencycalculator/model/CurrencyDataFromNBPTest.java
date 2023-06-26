package com.example.nbpcurrencycalculator.model;

import com.example.nbpcurrencycalculator.Service.CurrencyDataFromNBP;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class CurrencyDataFromNBPTest {
    @Autowired
    private CurrencyDataFromNBP currencyDataFromNBP;

    @Test
    void getCurrencyData() throws IOException, URISyntaxException, InterruptedException {
        //given
        NbpCurrency nbpCurrency =
                new NbpCurrency(CurrencyEnum.USD, "USD", null, null);
        Set<NbpCurrency> nbpCurrencySet = new HashSet<>();
        nbpCurrencySet.add(nbpCurrency);
        //when
        currencyDataFromNBP.getCurrencyData(nbpCurrencySet);
        //then

    }
}