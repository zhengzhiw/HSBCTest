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
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class StartMain {

    public static void main (String[] args) {
        ConcurrentHashMap<String, BigDecimal> bank = CurrencyBank.getPayment();
        MuServer server = muServer().withHttpPort(8090)
                .addHandler(Method.GET, "/currencyBank/all", (req, resp, pathParams) -> {
                    resp.contentType("text/html;charset=utf-8");
                    resp.sendChunk(new CurrencyService().printAmount().replaceAll("\\r\\n", "<br>"));
                }).addHandler(Method.GET, "/currencyBank/save", new SaveCurrencyHandler())
                .addHandler(Method.GET, "/{currency}/{amount: [0-9]+}/send/{count:[0-9]+}",
                        (request, response, pathParams) -> {
                            response.contentType("text/html;charset=utf-8");
                            SsePublisher publisher = SsePublisher.start(request, response);
                            response.contentType("text/html;charset=utf-8");
                            new Thread(() -> add(publisher, pathParams)).start();
                        }).start();
        System.out.println("Started server at " + server.uri());
        System.out.println("请输入一个绝对路径的文件");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        new CurrencyService().readFile(path);
    }

    public static void add (SsePublisher publisher, Map<String, String> pathParams) {
        String currencyStr = pathParams.get("currency");
        String amount = pathParams.get("amount");
        int count = Integer.parseInt(pathParams.get("count"));
        for (int i = 0; i < count; i++) {
            try {
                publisher.send("<br>正在存取第" + (i + 1) + "张钱<br>");
                CurrencyService currencyService = new CurrencyService();
                Currency currency = new Currency(currencyStr, new BigDecimal(amount));
                boolean flag = currencyService.save(currency);
                if (flag) {
                    publisher.send(new CurrencyService().printAmount().replaceAll("\\r\\n", "<br>"));
                } else {
                    publisher.send("更新失败");
                    break;
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                break;
            }
        }
        publisher.close();
    }

    ;
}
