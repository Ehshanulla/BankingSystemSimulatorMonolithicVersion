package com.service.transactionservice.processor;

import com.document.Account;
import com.document.Transaction;
import com.enums.TransactionStatus;
import com.enums.TransactionType;
import com.exceptions.AccountNotFoundException;
import com.repository.AccountRepository;
import com.service.accountservice.AccountServiceImpl;
import com.service.transactionservice.accountmanager.AccountBalanceManager;
import com.service.transactionservice.logs.DefaultTransactionLogService;
import com.service.transactionservice.validators.DefaultTransactionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DepositProcessor implements TransactionProcessor {
    private final AccountRepository accountRepo;
    private final DefaultTransactionLogService logService;
    private final DefaultTransactionValidator validator;
    private final AccountBalanceManager balanceManager;

    private static final Logger log = LoggerFactory.getLogger(DepositProcessor.class);

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
        Account acc = accountRepo.findByAccountNumber(t.getSourceAccountId()).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        balanceManager.credit(acc, t.getAmount());
        t.setStatus(TransactionStatus.SUCCESS);
        logService.save(t);
        log.info("Processing {} of {} for account {}", t.getType(), t.getAmount(), t.getSourceAccountId());
        acc.getTransactions().add(t);
        accountRepo.save(acc);
        return t;
    }
}
