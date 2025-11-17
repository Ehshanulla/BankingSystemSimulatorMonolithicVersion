package com.service.transactionservice.processor;

import com.document.Transaction;
import com.enums.TransactionType;
import com.service.accountservice.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface TransactionProcessor {
    TransactionType type();
    Transaction process(Transaction t);
}
