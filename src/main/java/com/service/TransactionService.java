package com.service;

import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    boolean deposit(String accountNumber, double amount);
    boolean withdraw(String accountNumber, double amount);
    boolean transfer(String from, String to, double amount);
}
