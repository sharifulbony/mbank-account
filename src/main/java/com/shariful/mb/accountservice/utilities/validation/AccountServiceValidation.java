package com.shariful.mb.accountservice.utilities.validation;

import com.shariful.mb.accountservice.entities.dbentities.Balance;
import com.shariful.mb.accountservice.utilities.util.CurrencyUtil;
import com.shariful.mb.accountservice.utilities.config.Constant;
import com.shariful.mb.accountservice.utilities.util.TransactionSourceDestination;

import java.math.BigDecimal;
import java.util.Objects;

import static java.lang.String.format;

public class AccountServiceValidation {

    private AccountServiceValidation() {
    }

    private static Validation<String> notNull = ValidationImpl.from(Objects::nonNull, Constant.NULL_ERROR_MESSAGE);
    private static Validation<BigDecimal> notNullAmount = ValidationImpl.from(Objects::nonNull, Constant.NULL_ERROR_MESSAGE);
    private static Validation<Balance> notNullBalance = ValidationImpl.from(Objects::nonNull, Constant.NULL_ERROR_MESSAGE);

    private static Validation<TransactionSourceDestination> notNullDirectionOfTransaction =
            ValidationImpl.from( Objects::nonNull, Constant.NULL_ERROR_MESSAGE);

    private static Validation<TransactionSourceDestination> validDirectionOfTransaction =
            ValidationImpl.from(s -> TransactionSourceDestination.contains(s.name()), Constant.INVALID_DIRECTION);
    private static Validation<String> checkValidityOfCurrency =
            ValidationImpl.from(s -> CurrencyUtil.isValidCurrency(s), Constant.INVALID_CURRENCY);

    private static Validation<BigDecimal> invalidAmount =
            ValidationImpl.from(s -> s.compareTo(BigDecimal.ZERO) != -1, Constant.INVALID_AMOUNT);

    public static Validation<BigDecimal> insufficientFunds(
            TransactionSourceDestination transactionSourceDestination,
            BigDecimal accountBalance
    ) {
        return ValidationImpl.from(transactionAmount -> !(transactionSourceDestination == TransactionSourceDestination.OUT &&
                accountBalance.compareTo(transactionAmount) == -1), format(Constant.INSUFFICIENT_FUNDS));
    }


    public static Validation<String> getNotNull() {
        return notNull;
    }

    public static Validation<BigDecimal> getNotNullAmount() {
        return notNullAmount;
    }

    public static Validation<Balance> getNotNullBalance() {
        return notNullBalance;
    }

    public static Validation<TransactionSourceDestination> getNotNullDirectionOfTransaction() {
        return notNullDirectionOfTransaction;
    }

    public static Validation<TransactionSourceDestination> getValidDirectionOfTransaction() {
        return validDirectionOfTransaction;
    }

    public static Validation<String> getCheckValidityOfCurrency() {
        return checkValidityOfCurrency;
    }

    public static Validation<BigDecimal> getInvalidAmount() {
        return invalidAmount;
    }

}
