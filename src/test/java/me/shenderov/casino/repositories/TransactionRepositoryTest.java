package me.shenderov.casino.repositories;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.TransactionEntity;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.enums.CurrencyCode;
import me.shenderov.casino.enums.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionRepositoryTest extends CasinoApplicationTests {
    @Test
    void saveTransaction() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        Transaction transaction = new Transaction(currency, 10.0, 20.0, TransactionStatus.NEW);
        TransactionEntity transactionEntity = new TransactionEntity(session, transaction, 1020.0);
        TransactionEntity transactionEntity1 = transactionRepository.save(transactionEntity);
        assertEquals(transactionEntity.getSession(), transactionEntity1.getSession());
        assertEquals(transactionEntity.getTransaction(), transactionEntity1.getTransaction());
        assertEquals(transactionEntity.getBalance(), transactionEntity1.getBalance());
        assertNotNull(transactionEntity1.getTimestamp());
    }

    @Test
    void saveTransactionNoSession() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Transaction transaction = new Transaction(currency, 10.0, 20.0, TransactionStatus.NEW);
        TransactionEntity transactionEntity = new TransactionEntity(null, transaction, 1020.0);
        assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.save(transactionEntity));
    }

    @Test
    void saveTransactionNoTransaction() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        TransactionEntity transactionEntity = new TransactionEntity(session, null, 1020.0);
        assertThrows(TransactionSystemException.class, () -> transactionRepository.save(transactionEntity));
    }
}