package com.service.accountservice;

import com.document.Account;
import com.document.Transaction;
import com.exceptions.AccountNotFoundException;
import com.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepo;
    public AccountServiceImpl(AccountRepository accountRepo){ this.accountRepo = accountRepo; }
    @Override public Account createAccount(String name){
        Account a = new Account();
        a.setAccountNumber(generateAccountNumber(name));
        a.setHolderName(name);
        a.setBalance(0);
        a.setStatus("ACTIVE");
        return accountRepo.save(a);
    }
    @Override
    public Account getAccount(String accountNumber){
        return accountRepo.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
    }
    @Override
    public List<Transaction> getAllTransactionIds(String accountId){
        return accountRepo.findById(accountId).map(Account::getTransactions).orElseGet(Collections::emptyList);
    }
    private String generateAccountNumber(String holderName) {
        String prefix = holderName.length() < 3 ? holderName.toUpperCase() : holderName.substring(0,3).toUpperCase(); int randomNum = (int)(Math.random()*9000)+1000; return prefix + randomNum;
    }
}

