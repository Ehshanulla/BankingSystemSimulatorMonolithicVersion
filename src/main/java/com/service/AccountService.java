package com.service;


import com.document.Account;
import com.document.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    Account createAccount(Account account);
    Account getAccount(String accountNumber);

    List<Transaction> getAllTransactions(String accountId);
}
