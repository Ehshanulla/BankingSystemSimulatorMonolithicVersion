package com.service.transactionservice.deposit;

import com.document.Transaction;
import org.springframework.stereotype.Service;


public interface TransactionServiceForDeposit {
    Transaction deposit(String accountId, double amount);
}
