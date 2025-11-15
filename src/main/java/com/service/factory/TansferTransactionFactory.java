package com.service.factory;

import com.document.Transaction;
import com.enums.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class TansferTransactionFactory implements TransactionFactoryForTransfer {
    @Override
    public Transaction create(TransactionType type, String src, String dest, double amount) {
        Transaction t = new Transaction();
        t.setType(type);
        t.setAmount(amount);
        t.setSourceAccountId(src);
        t.setDestinationAccountId(dest);
        return t;
    }
}
