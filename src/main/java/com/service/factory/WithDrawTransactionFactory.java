package com.service.factory;

import com.document.Transaction;
import com.enums.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class WithDrawTransactionFactory implements TransactionFactoryDepositAndWithdraw {
    @Override
    public Transaction create(TransactionType type, String src, double amount) {
        Transaction t = new Transaction();
        t.setType(type);
        t.setAmount(amount);
        t.setSourceAccountId(src);
        t.setDestinationAccountId("-");
        return t;
    }
}
