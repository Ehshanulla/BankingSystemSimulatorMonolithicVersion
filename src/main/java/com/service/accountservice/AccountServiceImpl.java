package com.service.accountservice;

import com.controller.AccountController;
import com.document.Account;
import com.document.Transaction;
import com.exceptions.AccountNotFoundException;
import com.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepo;
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    public AccountServiceImpl(AccountRepository accountRepo){ this.accountRepo = accountRepo; }


    @Override
    public Account createAccount(String name){
        Account a = new Account();
        a.setAccountNumber(generateAccountNumber(name));
        a.setHolderName(name);
        a.setCreatedAt(LocalDateTime.now());
        a.setBalance(0);
        a.setStatus("ACTIVE");
        log.debug("Fetching account {}", a.getAccountNumber());
        return accountRepo.save(a);
    }
    @Override
    public Account getAccount(String accountNumber){
        return accountRepo.findByAccountNumber(accountNumber).orElseThrow(()->new AccountNotFoundException("Account not found"));
    }
    @Override
    public List<Transaction> getAllTransactions(String accountId){
        return accountRepo.findByAccountNumber(accountId).map(Account::getTransactions).orElseGet(Collections::emptyList);
    }
    private String generateAccountNumber(String holderName) {
        String prefix = holderName.length() < 3 ? holderName.toUpperCase() : holderName.substring(0,3).toUpperCase(); int randomNum = (int)(Math.random()*9000)+1000; return prefix + randomNum;
    }

    @Override
    public Account updateAccount(Account account) {
        Optional<Account> acc = accountRepo.findByAccountNumber(account.getAccountNumber());
        if(account.getHolderName()!=null){
            if(acc.isPresent()) {
                acc.get().setHolderName(account.getHolderName());
                if(account.getBalance() != 0) acc.get().setBalance(account.getBalance());
                accountRepo.save(acc.get());
                return acc.get();
            }
        }
        return acc.orElseThrow(()->new AccountNotFoundException("Account not found"));
    }

    @Override
    public boolean deleteAccount(String accountNumber) {
        Optional<Account> acc = accountRepo.findByAccountNumber(accountNumber);
        if(acc.isPresent()){
            Account accFromDB = acc.get();
            accFromDB.setHolderName(null);
            accFromDB.setStatus("INACTIVE");
            accFromDB.setBalance(0);
            accountRepo.save(accFromDB);
            return true;
        }
        return false;
    }
}

