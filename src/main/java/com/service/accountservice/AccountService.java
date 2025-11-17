package com.service.accountservice;


import com.document.Account;
import com.document.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountService {
    Account createAccount(String name);
    Account getAccount(String accountNumber);
    List<Transaction> getAllTransactionIds(String accountId);
    Account updateAccount(Account account);
    boolean deleteAccount(String accountNumber);
}
