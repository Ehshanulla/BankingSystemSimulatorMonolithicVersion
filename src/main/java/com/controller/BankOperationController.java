package com.controller;

import com.document.Account;
import com.document.Transaction;
import com.document.dto.DepositRequest;
import com.document.dto.TransferRequest;
import com.service.AccountService;
import com.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class BankOperationController {

    private final AccountService accountService;


    private final TransactionService transactionService;

    public BankOperationController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping(value = "create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Account createAccount(@RequestBody Account holderName){
        return accountService.createAccount(holderName);
    }

    @GetMapping(value = "{accountNumber}")
    public Account getAccountById(@PathVariable("accountNumber") String accountNumber){
        return accountService.getAccount(accountNumber);
    }

    @PutMapping(value = "{accountNumber}/deposit",consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deposit( @PathVariable("accountNumber") String accountNumber, @RequestBody DepositRequest depositRequest){
        return transactionService.deposit(accountNumber,depositRequest.getAmount());
    }

    @PutMapping(value = "{accountNumber}/withdraw",consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean withdraw( @PathVariable("accountNumber") String accountNumber, @RequestBody DepositRequest depositRequest){
        return transactionService.withdraw(accountNumber,depositRequest.getAmount());
    }

    @PostMapping(value = "transfer",consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean transfer(@RequestBody TransferRequest transferRequest){
        return transactionService.transfer(transferRequest.getFromAccount(),transferRequest.getToAccount(),transferRequest.getAmount());
    }

    @GetMapping(value = "{accountNumber}/transactions")
    public List<Transaction> getAllTransactions(@PathVariable("accountNumber") String accountNumber){
        return accountService.getAllTransactions(accountNumber);
    }
}
