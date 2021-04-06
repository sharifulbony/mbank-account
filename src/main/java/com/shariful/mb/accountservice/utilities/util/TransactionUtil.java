package com.shariful.mb.accountservice.utilities.util;

 import com.shariful.mb.accountservice.entities.dbentities.Balance;
 import com.shariful.mb.accountservice.entities.dtos.TransactionDto;
 import com.shariful.mb.accountservice.utilities.config.Constant;
 import com.shariful.mb.accountservice.utilities.validation.AccountServiceValidation;

 import static com.shariful.mb.accountservice.utilities.validation.AccountServiceValidation.*;

public class TransactionUtil {

    private TransactionUtil() {
    }

    public static void checkTransactionValidity(
            TransactionDto transaction,
            Balance balanceByCurrency
    ){
        AccountServiceValidation.getNotNull().and(AccountServiceValidation.getCheckValidityOfCurrency()).test(transaction.getCurrency())
                .setMessage(Constant.INVALID_CURRENCY).throwIfInvalid();

        AccountServiceValidation.getNotNullBalance().test(balanceByCurrency)
                .setMessage(Constant.INVALID_CURRENCY).throwIfInvalid();

        AccountServiceValidation.getNotNullDirectionOfTransaction().and(AccountServiceValidation.getValidDirectionOfTransaction())
                .test(transaction.getTransactionSourceDestination())
                .setMessage(Constant.INVALID_DIRECTION).throwIfInvalid();

        AccountServiceValidation.getNotNull().test(transaction.getDescription())
                .setMessage(Constant.DESCRIPTION_MISSING).throwIfInvalid();

       AccountServiceValidation.getNotNullAmount().and(AccountServiceValidation.getInvalidAmount()).test(transaction.getAmount())
               .setMessage(Constant.INVALID_AMOUNT).throwIfInvalid();

       insufficientFunds(transaction.getTransactionSourceDestination(), balanceByCurrency.getCurrentBalance()).test(transaction.getAmount())
               .setMessage(Constant.INSUFFICIENT_FUNDS).throwIfInvalid();
    }
}
