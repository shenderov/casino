package me.shenderov.casino.handlers;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.TransactionEntity;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.enums.CurrencyCode;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.exceptions.CasinoOutOfBalanceException;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.exceptions.SessionOutOfBalanceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionHandlerTest extends CasinoApplicationTests {

    @Test
    void processTransaction() throws CasinoSetupException {
        Currency currency = new Currency(CurrencyCode.USD, CurrencyCode.USD.name, false, false);
        currency = currencyRepository.save(currency);
        CasinoBalance casinoBalance = new CasinoBalance(currency, 0, 1000, 0, 0, 0, 0);
        casinoBalance = casinoBalanceRepository.save(casinoBalance);
        Transaction transaction = new Transaction(currency, 10.0, 10.0, TransactionStatus.NEW);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(30));
        session = sessionRepository.save(session);
        TransactionEntity transactionEntity = transactionHandler.processTransaction(session.getuUID(), transaction);
        assertEquals(session.getuUID(), transactionEntity.getSession().getuUID());
        assertEquals(session.getBalance(), transactionEntity.getSession().getBalance());
        assertEquals(session.getCurrency(), transactionEntity.getSession().getCurrency());
        assertEquals(session.getName(), transactionEntity.getSession().getName());
        assertEquals(TransactionStatus.PROCESSED, transactionEntity.getTransaction().getStatus());
        assertEquals(currency, transactionEntity.getTransaction().getCurrency());
        assertEquals(10.0, transactionEntity.getTransaction().getWin());
        assertEquals(10.0, transactionEntity.getTransaction().getBet());
        assertEquals(1000, transactionEntity.getBalance());
        assertTrue(transactionEntity.getTimestamp() > 0);
        Optional<CasinoBalance> casinoBalance1 = casinoBalanceRepository.findByCurrency(currency);
        if(casinoBalance1.isPresent()){
            casinoBalance = casinoBalance1.get();
        }
        assertEquals(10.0, casinoBalance.getTotalWins());
        assertEquals(10.0, casinoBalance.getTotalBets());
        assertEquals(1, casinoBalance.getTransactionCounter());
        assertEquals(1, casinoBalance.getWinCounter());
        assertEquals(3.0000001192092896, casinoBalance.getBalance());
        assertEquals(996.9999998807907, casinoBalance.getWinBalance());
        transaction = new Transaction(currency, 10.0, 0.0, TransactionStatus.NEW);
        TransactionEntity transactionEntity1 = transactionHandler.processTransaction(session.getuUID(), transaction);
        assertEquals(TransactionStatus.PROCESSED, transactionEntity1.getTransaction().getStatus());
        casinoBalance1 = casinoBalanceRepository.findByCurrency(currency);
        if(casinoBalance1.isPresent()){
            casinoBalance = casinoBalance1.get();
        }
        assertEquals(10.0, casinoBalance.getTotalWins());
        assertEquals(20.0, casinoBalance.getTotalBets());
        assertEquals(2, casinoBalance.getTransactionCounter());
        assertEquals(1, casinoBalance.getWinCounter());
        assertEquals(6.000000238418579, casinoBalance.getBalance());
        assertEquals(1003.9999997615814, casinoBalance.getWinBalance());
        transaction = new Transaction(currency, 10.0, 20.0, TransactionStatus.NEW);
        TransactionEntity transactionEntity2 = transactionHandler.processTransaction(session.getuUID(), transaction);
        assertEquals(TransactionStatus.PROCESSED, transactionEntity2.getTransaction().getStatus());
        casinoBalance1 = casinoBalanceRepository.findByCurrency(currency);
        if(casinoBalance1.isPresent()){
            casinoBalance = casinoBalance1.get();
        }
        assertEquals(30.0, casinoBalance.getTotalWins());
        assertEquals(30.0, casinoBalance.getTotalBets());
        assertEquals(3, casinoBalance.getTransactionCounter());
        assertEquals(2, casinoBalance.getWinCounter());
        assertEquals(9.000000357627869, casinoBalance.getBalance());
        assertEquals(990.9999996423721, casinoBalance.getWinBalance());
        transaction = new Transaction(currency, 10.0, 200000.0, TransactionStatus.NEW);
        Transaction finalTransaction = transaction;
        Session finalSession = session;
        Exception exception = assertThrows(CasinoOutOfBalanceException.class, () -> transactionHandler.processTransaction(finalSession.getuUID(), finalTransaction));
        assertEquals("Casino out of win balance", exception.getMessage());
        casinoBalance1 = casinoBalanceRepository.findByCurrency(currency);
        if(casinoBalance1.isPresent()){
            casinoBalance = casinoBalance1.get();
        }
        assertEquals(30.0, casinoBalance.getTotalWins());
        assertEquals(30.0, casinoBalance.getTotalBets());
        assertEquals(3, casinoBalance.getTransactionCounter());
        assertEquals(2, casinoBalance.getWinCounter());
        assertEquals(9.000000357627869, casinoBalance.getBalance());
        assertEquals(990.9999996423721, casinoBalance.getWinBalance());
        transaction = new Transaction(currency, 200000.0, 0.0, TransactionStatus.NEW);
        Session finalSession1 = session;
        Transaction finalTransaction1 = transaction;
        exception = assertThrows(SessionOutOfBalanceException.class, () -> transactionHandler.processTransaction(finalSession1.getuUID(), finalTransaction1));
        assertEquals("Session out of balance", exception.getMessage());
        casinoBalance1 = casinoBalanceRepository.findByCurrency(currency);
        if(casinoBalance1.isPresent()){
            casinoBalance = casinoBalance1.get();
        }
        assertEquals(30.0, casinoBalance.getTotalWins());
        assertEquals(30.0, casinoBalance.getTotalBets());
        assertEquals(3, casinoBalance.getTransactionCounter());
        assertEquals(2, casinoBalance.getWinCounter());
        assertEquals(9.000000357627869, casinoBalance.getBalance());
        assertEquals(990.9999996423721, casinoBalance.getWinBalance());
        transactionRepository.delete(transactionEntity);
        transactionRepository.delete(transactionEntity1);
        transactionRepository.delete(transactionEntity2);
        sessionRepository.delete(session);
        casinoBalanceRepository.delete(casinoBalance);
        currencyRepository.delete(currency);
    }
}