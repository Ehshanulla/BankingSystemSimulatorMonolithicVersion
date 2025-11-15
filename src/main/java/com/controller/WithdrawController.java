package com.controller;

import com.document.Transaction;
import com.document.dto.DepositRequest;
import com.service.transactionservice.deposit.TransactionServiceForDeposit;
import com.service.transactionservice.deposit.TransactionServiceForDepositImpl;
import com.service.transactionservice.withdraw.TransactionServiceForWithdraw;
import com.service.transactionservice.withdraw.TransactionServiceForWithdrawImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class WithdrawController {
    private final TransactionServiceForWithdraw depositService;

    public WithdrawController(TransactionServiceForWithdrawImpl t){
        this.depositService = t;
    }

    @PutMapping(value = "{accountNumber}/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> withdraw(@PathVariable("accountNumber") String accountNumber, @RequestBody DepositRequest depositRequest){
        Transaction t = depositService.withdraw(accountNumber, depositRequest.getAmount());
        return ResponseEntity.ok(t);
    }
}
