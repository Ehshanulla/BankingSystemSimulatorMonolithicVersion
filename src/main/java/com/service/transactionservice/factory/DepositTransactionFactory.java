package com.service.transactionservice.factory;

import com.document.Transaction;
import com.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class DepositTransactionFactory implements TransactionFactoryDepositAndWithdraw {
    @Override
    public Transaction create(TransactionType type, String src, double amount) {

        Transaction t = new Transaction();
        t.setType(type);
        t.setTimestamp(LocalDateTime.now());
        t.setAmount(amount);
        t.setSourceAccountId(src);
        t.setDestinationAccountId("-");
        return t;
    }
}
