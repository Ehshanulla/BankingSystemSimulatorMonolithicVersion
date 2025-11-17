package com.service.transactionservice.logs;

import com.document.Transaction;
import com.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransactionLogService implements TransactionLogService {

    private final TxnSequenceService txnSequenceService;
    private final TransactionsRepository txnRepo;

    public DefaultTransactionLogService(TransactionsRepository txnRepo, TxnSequenceService txnSequenceService) {
        this.txnRepo = txnRepo;
        this.txnSequenceService = txnSequenceService;
    }

    @Override
    public Transaction save(Transaction t) {
        String txnId = generateTransactionId();
        t.setTransactionId(txnId);

        if (txnRepo != null) {
            return txnRepo.save(t);
        } else {
            return t;
        }
    }

    private String generateTransactionId() {
        int seq = txnSequenceService.nextSequence();
        String date = java.time.LocalDate.now().toString().replace("-", "");
        return "TXN-" + date + "-" + String.format("%03d", seq);
    }
}
