package me.shenderov.casino.repositories;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.enums.CurrencyCode;
import me.shenderov.casino.exceptions.InvalidCurrencyException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CasinoBalanceRepositoryTest extends CasinoApplicationTests {

    @Test
    void saveBalance() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        currency = currencyRepository.save(currency);
        CasinoBalance casinoBalance = new CasinoBalance();
        casinoBalance.setCurrency(currency);
        casinoBalance = casinoBalanceRepository.save(casinoBalance);
        assertEquals(casinoBalance.getCurrency(), currency);
        assertEquals(casinoBalance.getBalance(), 0);
        assertEquals(casinoBalance.getWinBalance(), 0);
        assertEquals(casinoBalance.getTotalBets(), 0);
        assertEquals(casinoBalance.getTotalWins(), 0);
        assertEquals(casinoBalance.getTransactionCounter(), 0);
        assertEquals(casinoBalance.getWinCounter(), 0);
        assertTrue(casinoBalanceRepository.existsById(casinoBalance.getId()));
        Optional<CasinoBalance> casinoBalance1 = casinoBalanceRepository.findByCurrency(currency);
        assertTrue(casinoBalance1.isPresent());
        assertEquals(casinoBalance1.get().getCurrency().getCurrencyCode(), currency.getCurrencyCode());
        casinoBalanceRepository.delete(casinoBalance);
        currencyRepository.delete(currency);
    }

    @Test
    void cannotCreateBalanceWithNotUniqueCurrency() {
        Optional<Currency> currency = currencyRepository.findById(CurrencyCode.CRD);
        if(currency.isEmpty()){
            throw new InvalidCurrencyException("Currency not found");
        }
        CasinoBalance casinoBalance = new CasinoBalance();
        casinoBalance.setCurrency(currency.get());
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> casinoBalanceRepository.save(casinoBalance));
        assertTrue(exception.getMessage().contains("could not execute statement; SQL [n/a]; constraint"));
    }

    @Test
    void cannotCreateBalanceNullCurrency() {
        CasinoBalance casinoBalance = new CasinoBalance();
        casinoBalance.setCurrency(null);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> casinoBalanceRepository.save(casinoBalance));
        assertTrue(exception.getMessage().contains("could not execute statement; SQL [n/a]; constraint"));
    }

    @Test
    void cannotDeleteAssignedBalance() {
        Optional<Currency> currency = currencyRepository.findById(CurrencyCode.CRD);
        if(currency.isEmpty()){
            throw new InvalidCurrencyException("Currency not found");
        }
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> currencyRepository.delete(currency.get()));
        assertTrue(exception.getMessage().contains("could not execute statement; SQL [n/a]; constraint"));
    }

}