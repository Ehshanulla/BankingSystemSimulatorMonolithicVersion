package com.service.transactionservice.withdraw;

import com.document.Transaction;
import org.springframework.stereotype.Service;


public interface TransactionServiceForWithdraw {
    Transaction withdraw(String accountId, double amount);
}
