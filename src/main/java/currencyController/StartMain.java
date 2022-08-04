package currencyController;

import currencyService.CurrencyService;
import entity.Currency;
import entity.CurrencyBank;
import handlers.SaveCurrencyHandler;
import io.muserver.Method;
import io.muserver.MuServer;
import static io.muserver.MuServerBuilder.muServer;
import io.muserver.SsePublisher;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class StartMain {

    public static void main (String[] args) {
        ConcurrentHashMap<String, BigDecimal> bank = CurrencyBank.getPayment();
        MuServer server = muServer().withHttpPort(8090)
                .addHandler(Method.GET, "/currencyBank/all", (req, resp, pathParams) -> {
                    resp.sendChunk(new CurrencyService().printAmount());
                }).addHandler(Method.GET, "/currencyBank/save", new SaveCurrencyHandler())
                .addHandler(Method.GET, "/{currency}/{amount: [0-9]+}/send", (request, response, pathParams) -> {
                    SsePublisher publisher = SsePublisher.start(request, response);
                    String currencyStr = pathParams.get("currency");
                    String amount = pathParams.get("amount");
                    CurrencyService currencyService = new CurrencyService();
                    Currency currency = new Currency(currencyStr,new BigDecimal(amount));
                    boolean flag = currencyService.save(currency);
                    if (flag) {
                        publisher.send(new CurrencyService().printAmount());
                    }else{
                        publisher.send("更新失败");
                    }
                }).start();
        System.out.println("Started server at " + server.uri());
        System.out.println("请输入一个绝对路径的文件");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        new CurrencyService().readFile(path);
    }

}
