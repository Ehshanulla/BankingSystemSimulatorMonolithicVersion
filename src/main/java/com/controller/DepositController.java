package com.controller;


import com.document.Transaction;
import com.document.dto.DepositRequest;
import com.service.accountservice.AccountService;
import com.service.transactionservice.deposit.TransactionServiceForDeposit;
import com.service.transactionservice.deposit.TransactionServiceForDepositImpl;
import com.service.transactionservice.transfer.TransactionServiceForTransfer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class DepositController {
    private final TransactionServiceForDeposit depositService;

    public DepositController(TransactionServiceForDepositImpl t){
        this.depositService = t;
    }

    @PutMapping(value = "{accountNumber}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> deposit(@PathVariable("accountNumber") String accountNumber, @RequestBody DepositRequest depositRequest){
        Transaction t = depositService.deposit(accountNumber, depositRequest.getAmount());
        return ResponseEntity.ok(t);
    }
}
