package currencyController;

import currencyService.CurrencyService;
import entity.CurrencyBank;
import handlers.SaveCurrencyHandler;
import io.muserver.*;
import static io.muserver.MuServerBuilder.muServer;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class StartMain {

    public static void main (String[] args) {
        ConcurrentHashMap<String, BigDecimal> bank = CurrencyBank.getPayment();
        MuServer server = muServer().withHttpPort(8090)
                .addHandler(Method.GET, "/getAmount/all", (req, resp, pathParams) -> {
                    resp.sendChunk(new CurrencyService().printAmount());
                })
                .addHandler(Method.GET, "/{currency}}/{amount}/save", new SaveCurrencyHandler()).start();
        System.out.println("Started server at " + server.uri());
        System.out.println("请输入一个绝对路径的文件");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        new CurrencyService().readFile(path);
    }

}
