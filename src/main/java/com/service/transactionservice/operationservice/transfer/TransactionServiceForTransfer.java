package com.service.transactionservice.operationservice.transfer;

import com.document.Transaction;
import org.springframework.stereotype.Service;


public interface TransactionServiceForTransfer {
    Transaction transfer(String from, String to, double amount);
}
