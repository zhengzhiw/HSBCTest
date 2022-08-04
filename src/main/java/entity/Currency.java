package entity;

import enums.CurrencyEnum;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 货币实例
 *
 * @author zhengzhiwen
 * @since 2022/08/04
 */
public class Currency {

    private String currencyCode;

    private BigDecimal amount;

    private Currency () {
    }

    public Currency (String currencyCode, BigDecimal amount) {
        validateCurrent(currencyCode);
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public String getCurrencyCode () {
        return currencyCode;
    }

    public void setCurrencyCode (String currencyCode) {
        validateCurrent(currencyCode);
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount () {
        return amount;
    }

    public void setAmount (BigDecimal amount) {
        this.amount = amount;
    }

    private void validateCurrent (String currencyCode) throws RuntimeException {
        Stream<CurrencyEnum> stream = Arrays.stream(CurrencyEnum.values());
        boolean flag = stream.anyMatch(e -> e.getCurrencyName().equals(currencyCode));
        if (!flag) {
            throw new RuntimeException("输入的货币种类不存在！");
        }
    }

    ;
}
