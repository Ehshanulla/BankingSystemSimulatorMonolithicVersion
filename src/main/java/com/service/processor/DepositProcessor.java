package com.service.processor;

import com.document.Account;
import com.document.Transaction;
import com.enums.TransactionStatus;
import com.enums.TransactionType;
import com.exceptions.AccountNotFoundException;
import com.repository.AccountRepository;
import com.service.accountmanager.AccountBalanceManager;
import com.service.logs.DefaultTransactionLogService;
import com.service.validators.DefaultTransactionValidator;
import org.springframework.stereotype.Service;

@Service
public class DepositProcessor implements TransactionProcessor {

    private final AccountRepository accountRepo;
    private final DefaultTransactionLogService logService;
    private final DefaultTransactionValidator validator;
    private final AccountBalanceManager balanceManager;

    public DepositProcessor(AccountRepository accountRepo, DefaultTransactionLogService logService, DefaultTransactionValidator validator,
                            AccountBalanceManager balanceManager){
        this.accountRepo = accountRepo; this.logService = logService; this.validator = validator; this.balanceManager = balanceManager;
    }

    @Override
    public TransactionType type() {
        return TransactionType.DEPOSIT;
    }

    @Override
    public Transaction process(Transaction t) {
        validator.validate(t);
        Account acc = accountRepo.findById(t.getSourceAccountId()).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        balanceManager.credit(acc, t.getAmount());
        accountRepo.save(acc);
        t.setStatus(TransactionStatus.SUCCESS);
        logService.save(t);
        acc.getTransactions().add(t); accountRepo.save(acc);
        return t;
    }
}
