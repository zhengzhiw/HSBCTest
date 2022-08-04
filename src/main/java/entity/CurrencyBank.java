package entity;

import enums.CurrencyEnum;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 币种银行，保存金额
 * @author zhengzhiwen
 * @since 2022/08/04
 */
public class CurrencyBank {
    private volatile static ConcurrentHashMap<String, BigDecimal> payment;

    public static ConcurrentHashMap<String, BigDecimal> getPayment () {
        if (payment == null) {
            synchronized (CurrencyBank.class) {
                if (payment == null) {
                    payment = new ConcurrentHashMap<String, BigDecimal>(CurrencyEnum.values().length);
                }
            }
        }
        return payment;
    }
}
