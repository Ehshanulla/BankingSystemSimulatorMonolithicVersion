package com.service.impl;

import com.document.Account;
import com.document.Transaction;
import com.exceptions.AccountNotFoundException;
import com.exceptions.InsufficientBalanceException;
import com.exceptions.InvalidAmountException;
import com.repository.AccountRepository;
import com.repository.TransactionsRepository;
import com.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionsRepository transactionsRepository;

    private final AccountRepository accountRepository;


    public TransactionServiceImplementation(TransactionsRepository transactionsRepository, AccountRepository accountRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountRepository = accountRepository;
    }




    @Override
    public boolean deposit(String accountNumber, double amount) {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive");

        Account acc = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("No account found"));

        acc.setBalance(acc.getBalance() + amount);
        Transaction txn = new Transaction();
        txn.setSourceAccountId(accountNumber);
        txn.setAmount(amount);
        txn.setType("DEPOSIT");
        txn.setStatus("Success");


        transactionsRepository.save(txn);
        acc.getTransactions().add(txn);

        accountRepository.save(acc);

        return true;
    }

    @Override
    public boolean withdraw(String accountNumber, double amount) {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive");


        Account acc = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));


        if (acc.getBalance() < amount) throw new InsufficientBalanceException("Insufficient funds");


        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);


        Transaction txn = new Transaction();
        txn.setSourceAccountId(accountNumber);
        txn.setAmount(amount);
        txn.setType("WITHDRAW");
        txn.setStatus("Success");

        transactionsRepository.save(txn);

        acc.getTransactions().add(txn);

        accountRepository.save(acc);
        return true;
    }

    @Override
    public boolean transfer(String from, String to, double amount) {
        if (amount <= 0) throw new InvalidAmountException("Amount must be positive");


        Account src = accountRepository.findById(from)
                .orElseThrow(() -> new AccountNotFoundException("Sender not found"));


        Account dest = accountRepository.findById(to)
                .orElseThrow(() -> new AccountNotFoundException("Receiver not found"));


        if (src.getBalance() < amount) throw new InsufficientBalanceException("Insufficient funds");


        src.setBalance(src.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);


        accountRepository.save(src);
        accountRepository.save(dest);


        Transaction txn = new Transaction();
        txn.setSourceAccountId(from);
        txn.setDestinationAccountId(to);
        txn.setAmount(amount);
        txn.setType("TRANSFER");
        txn.setStatus("Success");

        transactionsRepository.save(txn);

        src.getTransactions().add(txn);
        dest.getTransactions().add(txn);

        accountRepository.save(src);
        accountRepository.save(dest);

        return true;
    }
}
