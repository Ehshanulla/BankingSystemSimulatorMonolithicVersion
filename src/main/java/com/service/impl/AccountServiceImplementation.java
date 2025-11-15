package com.service.impl;

import com.document.Account;
import com.document.Transaction;
import com.exceptions.AccountNotFoundException;
import com.repository.AccountRepository;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImplementation implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        account.setAccountNumber(generateAccountNumber(account.getHolderName()));
        account.setHolderName(account.getHolderName());
        account.setBalance(0);
        account.setStatus("ACTIVE");
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
    }

    @Override
    public List<Transaction> getAllTransactions(String accountId){
        return accountRepository.findById(accountId)
                .map(Account::getTransactions)
                .orElseGet(Collections::emptyList);
    }

    private String generateAccountNumber(String holderName) {

        String prefix = holderName.length() < 3
                ? holderName.toUpperCase()
                : holderName.substring(0, 3).toUpperCase();


        int randomNum = (int) (Math.random() * 9000) + 1000;

        return prefix + randomNum;
    }
}
