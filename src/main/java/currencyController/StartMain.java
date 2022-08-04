package currencyController;

import currencyService.CurrencyService;
import entity.CurrencyBank;
import handlers.QueryStringExampleHandler;
import io.muserver.*;
import static io.muserver.MuServerBuilder.muServer;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class StartMain {

    public static void main (String[] args) {
        ConcurrentHashMap<String, BigDecimal> bank = CurrencyBank.getPayment();
        MuServer server = muServer().withHttpPort(8080)
                .addHandler(Method.POST, "/getAmount/all", (req, resp, pathParams) -> {
                    CurrencyService currencyService = new CurrencyService();
                    currencyService.printAmount();
                }).start();
        System.out.println("Started server at " + server.uri());
    }

}
