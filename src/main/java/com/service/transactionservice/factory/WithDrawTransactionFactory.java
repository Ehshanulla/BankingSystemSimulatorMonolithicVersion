package com.service.transactionservice.factory;

import com.document.Transaction;
import com.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WithDrawTransactionFactory implements TransactionFactoryDepositAndWithdraw {

    private final TxnSequenceService txnSequenceService;

    public WithDrawTransactionFactory(TxnSequenceService txnSequenceService){
        this.txnSequenceService = txnSequenceService;
    }
    @Override
    public Transaction create(TransactionType type, String src, double amount) {
        Transaction t = new Transaction();
        t.setTransactionId(txnSequenceService.generateTransactionId());
        t.setType(type);
        t.setTimestamp(LocalDateTime.now());
        t.setAmount(amount);
        t.setSourceAccountId(src);
        t.setDestinationAccountId("-");
        return t;
    }
}
