package com.controller;

import com.document.Transaction;
import com.document.dto.TransferRequest;
import com.service.accountservice.AccountService;
import com.service.transactionservice.transfer.TransactionServiceForTransfer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class TransferController {
    private final TransactionServiceForTransfer transactionService;

    public TransferController( TransactionServiceForTransfer t){
        this.transactionService = t;
    }

    @PostMapping(value = "transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequest transferRequest){
        Transaction t = transactionService.transfer(transferRequest.getFromAccount(), transferRequest.getToAccount(), transferRequest.getAmount());
        return ResponseEntity.ok(t);
    }
}
