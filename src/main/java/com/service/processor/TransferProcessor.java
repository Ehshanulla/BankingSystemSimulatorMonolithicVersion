package com.service.processor;

import com.document.Account;
import com.document.Transaction;
import com.enums.TransactionStatus;
import com.enums.TransactionType;
import com.exceptions.AccountNotFoundException;
import com.exceptions.InsufficientBalanceException;
import com.repository.AccountRepository;
import com.service.accountmanager.AccountBalanceManager;
import com.service.logs.DefaultTransactionLogService;
import com.service.validators.DefaultTransactionValidator;
import org.springframework.stereotype.Service;

@Service
public class TransferProcessor implements TransactionProcessor {
    private final AccountRepository accountRepo;
    private final DefaultTransactionValidator validator;
    private final DefaultTransactionLogService logService;
    private final AccountBalanceManager balanceManager;
    public TransferProcessor(AccountRepository a, DefaultTransactionValidator v, DefaultTransactionLogService l, AccountBalanceManager m){
        this.accountRepo=a;
        this.validator=v;
        this.logService=l;
        this.balanceManager=m;
    }
    @Override
    public TransactionType type(){ return TransactionType.TRANSFER; }
    @Override
    public Transaction process(Transaction t){
        validator.validate(t);
        Account src = accountRepo.findById(t.getSourceAccountId()).orElseThrow(() -> new AccountNotFoundException("Sender not found"));
        Account dst = accountRepo.findById(t.getDestinationAccountId()).orElseThrow(() -> new AccountNotFoundException("Receiver not found"));
        if (src.getBalance() < t.getAmount()){
            t.setStatus(TransactionStatus.FAILED);
            logService.save(t);
            src.getTransactions().add(t);
            dst.getTransactions().add(t);
            accountRepo.save(src);
            accountRepo.save(dst);
            throw new InsufficientBalanceException("Insufficient funds");
        }
        balanceManager.debit(src, t.getAmount());
        balanceManager.credit(dst, t.getAmount());
        accountRepo.save(src);
        accountRepo.save(dst);
        t.setStatus(TransactionStatus.SUCCESS);
        logService.save(t);
        src.getTransactions().add(t);
        dst.getTransactions().add(t);
        accountRepo.save(src);
        accountRepo.save(dst);
        return t;
    }
}
