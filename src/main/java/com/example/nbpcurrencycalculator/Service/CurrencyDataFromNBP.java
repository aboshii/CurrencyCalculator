package com.example.nbpcurrencycalculator.Service;

import com.example.nbpcurrencycalculator.model.NbpCurrency;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class CurrencyDataFromNBP {
    public Set<NbpCurrency> getCurrencyData(Set<NbpCurrency> nbpCurrencySet) throws IOException, URISyntaxException, InterruptedException {
        Set<NbpCurrency> newNbpCurrencySet = new HashSet<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse response;
        for (NbpCurrency nbpCurrency : nbpCurrencySet) {
            int daysSinceLastUpdate = 0;
            do {
                URL url = buildUrl(daysSinceLastUpdate, nbpCurrency);
                System.out.println(url);
                request = HttpRequest.newBuilder()
                        .GET()
                        .uri(url.toURI())
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                daysSinceLastUpdate++;
            } while (response.statusCode() == 404);
            String responseBody = response.body().toString();
            String bidValue = getBidValueFromJson(responseBody);
            String askValue = getAskValueFromJson(responseBody);
            nbpCurrency.setBid(bidValue);
            nbpCurrency.setAsk(askValue);
            newNbpCurrencySet.add(nbpCurrency);
        }
        return newNbpCurrencySet;
    }

    private String getAskValueFromJson(String responseBody) {
        return formatDoubleValueFromJson(
                JsonPath.read(responseBody, "$.['rates'][:1].['ask']")
                        .toString());
    }

    private String getBidValueFromJson(String responseBody) {
        return formatDoubleValueFromJson(
                JsonPath.read(responseBody, "$.['rates'][:1].['bid']")
                        .toString());
    }

    private URL buildUrl(int i, NbpCurrency nbpCurrency) throws MalformedURLException {
        URL url = new URL(
                "http://api.nbp.pl/api/exchangerates/rates/c/" +
                        nbpCurrency.getName() + '/' +
                        LocalDate.now().getYear() + "-" +
                        String.format("%02d", LocalDate.now().getMonthValue()) + "-" +
                        ((LocalDate.now().getDayOfMonth()) - i) + "/" +
                        "?format=json");
        return url;
    }
    private String formatDoubleValueFromJson(String bidValue) {
        bidValue = bidValue.replace("[", "");
        bidValue = bidValue.replace("]", "");
        return bidValue;
    }
}
