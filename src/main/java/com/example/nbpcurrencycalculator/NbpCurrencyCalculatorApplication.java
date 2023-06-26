package com.example.nbpcurrencycalculator;

import com.example.nbpcurrencycalculator.model.NbpCurrency;
import com.example.nbpcurrencycalculator.model.NbpCurrencyCalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NbpCurrencyCalculatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NbpCurrencyCalculatorApplication.class, args);
//        NbpCurrencyCalculator bean = context.getBean(NbpCurrencyCalculator.class);
//        bean.updateNbpCurrencyData();
    }
}
