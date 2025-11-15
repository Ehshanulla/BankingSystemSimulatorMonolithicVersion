package com.controller;

import com.document.Account;
import com.document.Transaction;
import com.service.accountservice.AccountService;
import com.service.transactionservice.transfer.TransactionServiceForTransfer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService a){
        this.accountService = a;
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody String holderName){
        return ResponseEntity.ok(accountService.createAccount(holderName));
    }

    @GetMapping(value = "{accountNumber}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }


    @GetMapping(value = "{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(accountService.getAllTransactionIds(accountNumber)); }
}
