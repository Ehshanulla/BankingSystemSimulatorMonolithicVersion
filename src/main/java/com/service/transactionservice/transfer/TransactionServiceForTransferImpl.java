package com.service.transactionservice.transfer;

import com.document.Transaction;
import com.enums.TransactionType;
import com.service.factory.TansferTransactionFactory;
import com.service.factory.TransactionFactoryForTransfer;
import com.service.processor.TransactionProcessor;
import com.service.processor.TransferProcessor;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceForTransferImpl implements TransactionServiceForTransfer {
    private final TransactionFactoryForTransfer factory;
    private final TransactionProcessor processor;

    public TransactionServiceForTransferImpl(TansferTransactionFactory factory, TransferProcessor transferProcessor){
        this.factory = factory;
        this.processor=transferProcessor;
    }

    @Override
    public Transaction transfer(String from, String to, double amount){
        Transaction t = factory.create(TransactionType.TRANSFER, from, to, amount);
        return processor.process(t);
    }
}
