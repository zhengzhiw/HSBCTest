package currencyService;

import entity.Currency;
import entity.CurrencyBank;
import io.netty.util.internal.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyService {
    ConcurrentHashMap<String, BigDecimal> bank = CurrencyBank.getPayment();

    public void readFile (String readPath) {
        if (readPath == null) {
            System.out.println("用户跳过文件读取");
            return;
        }
        File file = new File(readPath);
        FileReader fileReader = null;
        boolean exists = file.exists();
        if (exists) {
            try {
                fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String readLine = null;
                if ((readLine = bufferedReader.readLine()) != null) {
                    String[] strings = readLine.split(" ");
                    if (strings.length >= 2) {
                        Currency currency = new Currency(strings[0], new BigDecimal(strings[1]));
                        save(currency);
                    }
                }
            } catch (IOException e) {
                System.out.println("文件格式错误。跳过文件读取...");
            }
        } else {
            System.out.println("文件不存在");
        }
        timerPrint();
        openScan();
    }

    public void openScan(){
        while (true){
            System.out.println("请输入金额,如:HKD 100");
            // 监听控制台数据
            Scanner sc = new Scanner(System.in);
            //读取字符串型输入
            String nextLine = sc.nextLine();
            if ("quit".equals(nextLine)){
                System.exit(1);
                return;
            }
            String[] strings = nextLine.split(" ");
            if (strings.length == 2){
                Currency currency = new Currency(strings[0],new BigDecimal(strings[1]));
                save(currency);
            }else{
                System.out.println("输入的格式不正确");
            }
        }
    }

    public boolean save (Currency currency) {
        String currencyCode = currency.getCurrencyCode();
        if (StringUtil.isNullOrEmpty(currencyCode)){
            return false;
        }
        boolean containsKey = bank.containsKey(currencyCode);
        if (containsKey) {
            bank.put(currencyCode, bank.get(currencyCode).add(currency.getAmount()));
        } else {
            bank.put(currencyCode, currency.getAmount());
        }
        if (bank.get(currencyCode).compareTo(BigDecimal.ZERO) == 0) {
            bank.remove(currencyCode);
        }
        System.out.println(printAmount());
        return true;
    }
    public void timerPrint(){
        // 开启打印输出任务
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(printAmount());
            }
        },0,60000);
    }
    public String printAmount () {
        StringBuilder paymentStr = new StringBuilder();
        paymentStr.append("------" + new Date(System.currentTimeMillis()).toString() + " 最新金额统计如下 ------");
        bank.entrySet().stream().forEach(e -> {
            paymentStr.append("\r\n");
            paymentStr.append(e.getKey() + " " + e.getValue());
        });
        return paymentStr.toString();
    }
}
