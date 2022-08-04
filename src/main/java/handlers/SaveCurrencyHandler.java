package handlers;

import currencyService.CurrencyService;
import entity.Currency;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

import java.math.BigDecimal;
import java.util.Map;

public class SaveCurrencyHandler implements RouteHandler {
    public void handle(MuRequest request, MuResponse response, Map<String,String> pathParams) {
        String currencyStr = request.query().get("currency");
        double amount = request.query().getDouble("amount", 0);
        CurrencyService currencyService = new CurrencyService();
        Currency currency = new Currency(currencyStr,new BigDecimal(amount));
        currencyService.save(currency);
        response.sendChunk(currencyService.printAmount());
    }
}