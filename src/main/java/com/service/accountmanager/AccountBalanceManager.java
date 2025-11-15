package com.service.accountmanager;

import com.document.Account;

public interface AccountBalanceManager { void credit(Account acc, double amount); void debit(Account acc, double amount); }
