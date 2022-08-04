package enums;

/**
 * @author zhengzhiwen
 * @since 2022/08/04
 */
public enum CurrencyEnum {

    USD("USD"), HKD("HKD"), CNY("CNY");

    private final String currencyName;

    private CurrencyEnum (String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName () {
        return currencyName;
    }

}