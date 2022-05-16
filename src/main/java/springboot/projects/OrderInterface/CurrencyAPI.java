package springboot.projects.OrderInterface;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CurrencyAPI {
    public static double convertCurrency(double amount, String baseCountry, String newCountry) {
        String baseCurrency = null;
        switch (baseCountry) {
            case "Germany" -> baseCurrency = "EUR";
            case "Singapore" -> baseCurrency = "SGD";
        }
        String newCurrency = null;
        switch (newCountry) {
            case "Germany" -> newCurrency = "EUR";
            case "Singapore" -> newCurrency = "SGD";
        }

        double exchangeRate = 0.0;

        return amount * exchangeRate;
    }

    private static double getExchangeRate() {
        String jsonString = "";

        try {
            InputStream is = new ByteArrayInputStream(jsonString.getBytes());
//            JsonReader reader = Json.createReader(is);
//            JsonObject data = reader.readObject();
            // TODO: Parse data, get exchange rate
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}