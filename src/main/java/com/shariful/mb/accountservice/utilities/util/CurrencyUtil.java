package com.shariful.mb.accountservice.utilities.util;
import com.shariful.mb.accountservice.utilities.config.Constant;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

 public class CurrencyUtil{


     private CurrencyUtil() {
     }

    private static final Set<String> CURRENCY_LIST = new HashSet<>(Constant.CURRENCY_CODE_LIST);
    public static boolean isValidCurrency(String currencyCode){
        return CurrencyUtil.CURRENCY_LIST.contains(currencyCode);
    }
    public static void checkValidCurrency(List<String> currencies)
            throws InvalidParameterException {
        List<String> invalidCurrencies = new ArrayList<>();
        for(String currencyCode : currencies){
            if(!isValidCurrency(currencyCode)){
                invalidCurrencies.add(currencyCode);
            }
        }
        if(!invalidCurrencies.isEmpty()){
            throw new InvalidParameterException(Constant.INVALID_CURRENCY + " : "
                    + Arrays.toString(invalidCurrencies.toArray()));
        }
    }
}
