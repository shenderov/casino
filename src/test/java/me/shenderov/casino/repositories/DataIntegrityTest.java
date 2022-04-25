package me.shenderov.casino.repositories;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.TransactionEntity;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.enums.CurrencyCode;
import me.shenderov.casino.enums.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataIntegrityTest extends CasinoApplicationTests {

    @Test
    void setup() {
        //Create currency
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        currency = currencyRepository.save(currency);
        //Create casino balance
        CasinoBalance casinoBalance = new CasinoBalance();
        casinoBalance.setCurrency(currency);
        casinoBalance = casinoBalanceRepository.save(casinoBalance);
        CasinoBalance casinoBalance1 = new CasinoBalance();
        casinoBalance1.setCurrency(currency);
        //Create session
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        //Create transaction
        Transaction transaction = new Transaction(currency, 10.0, 20.0, TransactionStatus.NEW);
        TransactionEntity transactionEntity = new TransactionEntity(session, transaction, 1020.0);
        transactionEntity = transactionRepository.save(transactionEntity);
        //Cannot create 2 casino balances with the same currency
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> casinoBalanceRepository.save(casinoBalance1));
        assertTrue(exception.getMessage().contains("could not execute statement; SQL [n/a]; constraint"));
        //Cannot delete currency when assigned
        Currency finalCurrency = currency;
        exception = assertThrows(DataIntegrityViolationException.class, () -> currencyRepository.delete(finalCurrency));
        assertTrue(exception.getMessage().contains("could not execute statement; SQL [n/a]; constraint"));
        //delete casino balance - currency is not deleted
        casinoBalanceRepository.delete(casinoBalance);
        assertTrue(currencyRepository.existsById(CurrencyCode.CAD));
        //delete transaction - currency is not deleted
        transactionRepository.delete(transactionEntity);
        assertTrue(currencyRepository.existsById(CurrencyCode.CAD));
        //delete session - currency is not deleted
        sessionRepository.delete(session);
        assertTrue(currencyRepository.existsById(CurrencyCode.CAD));
        //delete not assigned currency - currency is deleted
        currencyRepository.delete(finalCurrency);
        assertFalse(currencyRepository.existsById(CurrencyCode.CAD));
    }

}