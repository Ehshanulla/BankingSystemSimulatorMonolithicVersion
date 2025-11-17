package com.main.service;


import com.document.Account;
import com.document.Transaction;
import com.enums.TransactionStatus;
import com.enums.TransactionType;
import com.exceptions.InsufficientBalanceException;
import com.exceptions.InvalidAmountException;
import com.repository.AccountRepository;
import com.service.transactionservice.accountmanager.AccountBalanceManager;
import com.service.transactionservice.logs.DefaultTransactionLogService;
import com.service.transactionservice.processor.DepositProcessor;
import com.service.transactionservice.processor.TransferProcessor;
import com.service.transactionservice.processor.WithdrawProcessor;
import com.service.transactionservice.validators.DefaultTransactionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionsValidationsTest {

    private final AccountRepository repo = Mockito.mock(AccountRepository.class);
    private final DefaultTransactionLogService log = Mockito.mock(DefaultTransactionLogService.class);
    private final AccountBalanceManager balanceManager = Mockito.mock(AccountBalanceManager.class);
    private final DefaultTransactionValidator validator = new DefaultTransactionValidator();

    // --------------------- DepositProcessor ---------------------
    @Test
    void testDepositSuccess() {
        DepositProcessor depositProcessor = new DepositProcessor(repo, log, validator, balanceManager);

        Account acc = new Account();
        acc.setAccountNumber("A");
        acc.setBalance(0);
        Mockito.when(repo.findByAccountNumber("A")).thenReturn(Optional.of(acc));

        Transaction t = new Transaction();
        t.setTransactionId("TXN-001");
        t.setSourceAccountId("A");
        t.setAmount(500);
        t.setType(TransactionType.DEPOSIT);

        Transaction result = depositProcessor.process(t);
        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
    }

    // --------------------- WithdrawProcessor ---------------------
    @Test
    void testWithdrawSuccess() {
        WithdrawProcessor withdrawProcessor = new WithdrawProcessor(repo, validator, log, balanceManager);

        Account acc = new Account();
        acc.setAccountNumber("B");
        acc.setBalance(1000);
        Mockito.when(repo.findByAccountNumber("B")).thenReturn(Optional.of(acc));

        Transaction t = new Transaction();
        t.setTransactionId("TXN-002");
        t.setSourceAccountId("B");
        t.setAmount(500);
        t.setType(TransactionType.WITHDRAW);

        Transaction result = withdrawProcessor.process(t);
        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
    }

    @Test
    void testWithdrawInsufficientBalance() {
        WithdrawProcessor withdrawProcessor = new WithdrawProcessor(repo, validator, log, balanceManager);

        Account acc = new Account();
        acc.setAccountNumber("C");
        acc.setBalance(100);
        Mockito.when(repo.findByAccountNumber("C")).thenReturn(Optional.of(acc));

        Transaction t = new Transaction();
        t.setTransactionId("TXN-003");
        t.setSourceAccountId("C");
        t.setAmount(500);

        assertThrows(InsufficientBalanceException.class, () -> withdrawProcessor.process(t));
    }

    // --------------------- TransferProcessor ---------------------
    @Test
    void testTransferSuccess() {
        TransferProcessor transferProcessor = new TransferProcessor(repo, validator, log, balanceManager);

        Account src = new Account();
        src.setAccountNumber("SRC");
        src.setBalance(1000);

        Account dst = new Account();
        dst.setAccountNumber("DST");
        dst.setBalance(100);

        Mockito.when(repo.findByAccountNumber("SRC")).thenReturn(Optional.of(src));
        Mockito.when(repo.findByAccountNumber("DST")).thenReturn(Optional.of(dst));

        Transaction t = new Transaction();
        t.setTransactionId("TXN-004");
        t.setSourceAccountId("SRC");
        t.setDestinationAccountId("DST");
        t.setAmount(200);
        t.setType(TransactionType.TRANSFER);

        Transaction result = transferProcessor.process(t);

        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
        Mockito.verify(balanceManager).debit(src, 200);
        Mockito.verify(balanceManager).credit(dst, 200);
    }

    // --------------------- Transaction Validator ---------------------
    @Test
    void testValidatorInvalidAmount() {
        Transaction t = new Transaction();
        t.setTransactionId("TXN-005");
        t.setAmount(0);

        assertThrows(InvalidAmountException.class, () -> validator.validate(t));
    }

    @Test
    void testValidatorValidAmount() {
        Transaction t = new Transaction();
        t.setTransactionId("TXN-006");
        t.setAmount(100);

        // Should not throw
        validator.validate(t);
        assertEquals(100, t.getAmount());
    }
}

