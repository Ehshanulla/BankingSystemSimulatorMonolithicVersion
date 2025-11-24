package com.service.transactionservice.factory;

import com.document.Transaction;
import com.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TansferTransactionFactory implements TransactionFactoryForTransfer {

    private final TxnSequenceService txnSequenceService;

    public TansferTransactionFactory(TxnSequenceService txnSequenceService){
        this.txnSequenceService = txnSequenceService;
    }

    @Override
    public Transaction create(TransactionType type, String src, String dest, double amount) {
        Transaction t = new Transaction();
        String txnId = txnSequenceService.generateTransactionId();
        t.setTransactionId(txnId);
        t.setType(type);
        t.setAmount(amount);
        t.setTimestamp(LocalDateTime.now());
        t.setSourceAccountId(src);
        t.setDestinationAccountId(dest);
        return t;
    }
}
