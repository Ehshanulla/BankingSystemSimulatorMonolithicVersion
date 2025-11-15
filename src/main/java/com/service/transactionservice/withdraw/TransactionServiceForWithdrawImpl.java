package com.service.transactionservice.withdraw;

import com.document.Transaction;
import com.enums.TransactionType;
import com.service.factory.TansferTransactionFactory;
import com.service.factory.TransactionFactoryDepositAndWithdraw;
import com.service.factory.TransactionFactoryForTransfer;
import com.service.factory.WithDrawTransactionFactory;
import com.service.processor.TransactionProcessor;
import com.service.processor.WithdrawProcessor;
import com.service.transactionservice.transfer.TransactionServiceForTransfer;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceForWithdrawImpl implements TransactionServiceForWithdraw {
    private final TransactionFactoryDepositAndWithdraw factory;
    private final TransactionProcessor processor;

    public TransactionServiceForWithdrawImpl(WithDrawTransactionFactory factory, WithdrawProcessor withdrawProcessor){
        this.factory = factory;
        this.processor=withdrawProcessor;
    }

    @Override
    public Transaction withdraw(String from, double amount){
        Transaction t = factory.create(TransactionType.WITHDRAW, from, amount);
        return processor.process(t);
    }
}
