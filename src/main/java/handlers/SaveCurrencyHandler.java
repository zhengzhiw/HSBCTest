package handlers;

import currencyService.CurrencyService;
import entity.Currency;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

import java.math.BigDecimal;
import java.util.Map;

public class SaveCurrencyHandler implements RouteHandler {
    @Override
    public void handle(MuRequest request, MuResponse response, Map<String,String> pathParams) {
        response.contentType("text/html;charset=utf-8");
        saveRequest(request);
        response.sendChunk(new CurrencyService().printAmount().replaceAll("\\r\\n","<br>"));
    }

    public Boolean saveRequest (MuRequest request) {
        String currencyStr = request.query().get("currency");
        double amount = request.query().getDouble("amount", 0);
        CurrencyService currencyService = new CurrencyService();
        Currency currency = new Currency(currencyStr,new BigDecimal(amount));
        return currencyService.save(currency);
    }
}