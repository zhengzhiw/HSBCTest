import currencyService.CurrencyService;
import entity.Currency;
import entity.CurrencyBank;
import enums.CurrencyEnum;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class UnitTest {
    @Test
    public void test () {
        URL resource = getClass().getClassLoader().getResource("data.txt");
        CurrencyService currencyService = new CurrencyService();
        assert resource != null;
        currencyService.readFile(resource.getFile(),true);
        ConcurrentHashMap<String, BigDecimal> payment = CurrencyBank.getPayment();
        boolean cny = payment.get(CurrencyEnum.CNY.getCurrencyName()).intValue() == 2000;
        boolean hkd = payment.get(CurrencyEnum.HKD.getCurrencyName()).intValue() == 300;
        boolean usd = payment.get(CurrencyEnum.USD.getCurrencyName()).intValue() == 900;
        Assert.assertTrue(cny);
        Assert.assertTrue(hkd);
        Assert.assertTrue(usd);
        currencyService.save(new Currency(CurrencyEnum.valueOf("CNY").getCurrencyName(),BigDecimal.valueOf(500)));
        currencyService.save(new Currency(CurrencyEnum.valueOf("HKD").getCurrencyName(),BigDecimal.valueOf(-500)));
        currencyService.save(new Currency(CurrencyEnum.valueOf("USD").getCurrencyName(),BigDecimal.valueOf(-900)));
        boolean cny1 = payment.get(CurrencyEnum.CNY.getCurrencyName()).intValue() == 2500;
        boolean hkd1 = payment.get(CurrencyEnum.HKD.getCurrencyName()).intValue() == -200;
        boolean usd1 = payment.get(CurrencyEnum.USD.getCurrencyName()) == null;
        Assert.assertTrue(cny1);
        Assert.assertTrue(hkd1);
        Assert.assertTrue(usd1);
    }
}
