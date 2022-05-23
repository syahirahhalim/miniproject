package springboot.projects.OrderInterface.services;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class CurrencyAPI {
    public static double convertCurrency(double amount, String baseCountry, String newCountry) {
        double exchangeRate = getExchangeRate(baseCountry, newCountry);
        return amount * exchangeRate;
    }

    private static double getExchangeRate(String baseCountry, String newCountry) {
        try {
            String jsonString = "";
            String url;
            if (baseCountry == null) {
                url = "https://api.exchangerate.host/latest?base=SGD";
            } else {
                url = "https://api.exchangerate.host/latest?base=" + baseCountry;
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonString = response.body();

            InputStream is = new ByteArrayInputStream(jsonString.getBytes());
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            JsonObject rates = data.getJsonObject("rates");

            return Double.valueOf(rates.get(newCountry).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}