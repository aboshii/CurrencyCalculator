package com.example.nbpcurrencycalculator.model;

import com.example.nbpcurrencycalculator.Service.CurrencyDataFromNBP;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.HashSet;

@Service
@Getter
public class NbpCurrencyCalculator {
    private Set<NbpCurrency> nbpCurrencySet = new HashSet<>();
    @Autowired
    CurrencyDataFromNBP currencyDataFromNBP;
    boolean updateRequest = true;

    public NbpCurrencyCalculator(){
        for (CurrencyEnum value : CurrencyEnum.values()) {
            nbpCurrencySet.add(
                    new NbpCurrency(value, value.name(), null, null)
            );
        }
    }
    @Scheduled(cron = "0 0 6 * * *")
    private void setUpdateRequestToTrue(){
        System.out.println("Update necessary!");
        this.updateRequest = true;
    }
    public BigDecimal calculateValue(BigDecimal value, String actualCurrency, String targetCurrency) throws IllegalArgumentException{
        checkUpdateNecessity();
        checkCodes(actualCurrency, targetCurrency);
        double ask = 0.0;
        double bid = 0.0;
        for (NbpCurrency nbpCurrency : nbpCurrencySet) {
            if (nbpCurrency.getName().equals(actualCurrency) && ask == 0.0){
                ask = Double.parseDouble(nbpCurrency.getAsk());
            }
            if (nbpCurrency.getName().equals(targetCurrency) && bid == 0.0){
                bid = Double.parseDouble(nbpCurrency.getBid());
            }
            if (ask != 0 && bid !=0) {
                break;
            }
        }
        return value.multiply(BigDecimal.valueOf(ask / bid));
    }

    private void checkCodes(String actualCurrency, String targetCurrency) throws IllegalArgumentException {
        try {
            checkCurrencyCode(actualCurrency, targetCurrency);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }

    private void checkUpdateNecessity() {
        if (updateRequest){
            updateNbpCurrencyData();
            System.out.println("Updated values");
        }
    }

    private boolean checkCurrencyCode(String actualCurrency, String targetCurrency) {
        boolean actualCurrencyCodeCorrect = false;
        boolean targetCurrencyCodeCorrect = false;
        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.name().equals(actualCurrency)) actualCurrencyCodeCorrect = true;
            if (value.name().equals(targetCurrency)) targetCurrencyCodeCorrect = true;
            if (actualCurrencyCodeCorrect && targetCurrencyCodeCorrect){
                return true;
            }
        }
        throw new IllegalArgumentException("Those codes are not supported");
    }


    public void updateNbpCurrencyData(){
        try {
            nbpCurrencySet = currencyDataFromNBP.getCurrencyData(nbpCurrencySet);
            updateRequest = false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
//            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
//            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
//            throw new RuntimeException(e);
        }
    }
}
