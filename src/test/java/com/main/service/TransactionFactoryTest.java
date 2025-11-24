package com.main.service;


import com.document.Transaction;
import com.enums.TransactionType;
import com.service.transactionservice.factory.DepositTransactionFactory;
import com.service.transactionservice.factory.TxnSequenceService;
import com.service.transactionservice.factory.WithDrawTransactionFactory;
import com.service.transactionservice.factory.TansferTransactionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

    @Mock
    TxnSequenceService txnSequenceService;

    @Test
    void testDepositTransactionFactory() {
        DepositTransactionFactory factory = new DepositTransactionFactory(txnSequenceService);
        Transaction t = factory.create(TransactionType.DEPOSIT,"A1", 500);

        assertNotNull(t);
        assertEquals("A1", t.getSourceAccountId());
        assertEquals(500, t.getAmount());
        assertEquals(TransactionType.DEPOSIT, t.getType());
    }

    @Test
    void testWithdrawTransactionFactory() {
        WithDrawTransactionFactory factory = new WithDrawTransactionFactory(txnSequenceService);
        Transaction t = factory.create(TransactionType.WITHDRAW,"A1", 300);

        assertNotNull(t);
        assertEquals("A1", t.getSourceAccountId());
        assertEquals(300, t.getAmount());
        assertEquals(TransactionType.WITHDRAW, t.getType());
    }

    @Test
    void testTransferTransactionFactory() {
        TansferTransactionFactory factory = new TansferTransactionFactory(txnSequenceService);
        Transaction t = factory.create(TransactionType.TRANSFER,"A1", "A2", 200);

        assertNotNull(t);
        assertEquals("A1", t.getSourceAccountId());
        assertEquals("A2", t.getDestinationAccountId());
        assertEquals(200, t.getAmount());
        assertEquals(TransactionType.TRANSFER, t.getType());
    }
}
