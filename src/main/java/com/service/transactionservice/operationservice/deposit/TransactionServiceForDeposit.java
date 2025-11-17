package com.service.transactionservice.operationservice.deposit;

import com.document.Transaction;


public interface TransactionServiceForDeposit {
    Transaction deposit(String accountId, double amount);
}
