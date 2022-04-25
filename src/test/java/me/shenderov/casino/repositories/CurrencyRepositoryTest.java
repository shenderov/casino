package me.shenderov.casino.repositories;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.enums.CurrencyCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyRepositoryTest extends CasinoApplicationTests {

    @Test
    void saveCurrency() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        Currency currency1 = currencyRepository.save(currency);
        assertEquals(currency.getCurrencyCode(), currency1.getCurrencyCode());
        assertEquals(currency.getCurrencyName(), currency1.getCurrencyName());
        assertEquals(currency.isVirtual(), currency1.isVirtual());
        assertEquals(currency.isFree(), currency1.isFree());
        currencyRepository.delete(currency1);
    }

    @Test
    void saveCurrencyNullId() {
        Currency currency = new Currency(null, CurrencyCode.CAD.name, false, false);
        Exception exception = assertThrows(JpaSystemException.class, () -> currencyRepository.save(currency));
        assertTrue(exception.getMessage().contains("ids for this class must be manually assigned before calling save()"));
    }

    @Test
    void saveCurrencyNullName() {
        Currency currency = new Currency(CurrencyCode.CAD, null, false, false);
        assertThrows(DataIntegrityViolationException.class, () -> currencyRepository.save(currency));
    }

    @Test
    void saveCurrencyNullIsVirtual() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, null, false);
        assertThrows(DataIntegrityViolationException.class, () -> currencyRepository.save(currency));
    }

    @Test
    void saveCurrencyNullIsFree() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, null);
        assertThrows(DataIntegrityViolationException.class, () -> currencyRepository.save(currency));
    }

    @Test
    void updateCurrencyCode() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        Currency currency1 = currencyRepository.save(currency);
        currency1.setCurrencyCode(CurrencyCode.USD);
        currencyRepository.save(currency1);
        currencyRepository.delete(currency1);
    }

    @Test
    void updateCurrencyName() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        Currency currency1 = currencyRepository.save(currency);
        currency1.setCurrencyName("USD");
        currency1 = currencyRepository.save(currency1);
        assertEquals("USD", currency1.getCurrencyName());
        currencyRepository.delete(currency1);
    }

    @Test
    void updateIsVirtual() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        Currency currency1 = currencyRepository.save(currency);
        currency1.setVirtual(true);
        currency1 = currencyRepository.save(currency1);
        assertTrue(currency1.isVirtual());
        currencyRepository.delete(currency1);
    }

    @Test
    void updateIsFree() {
        Currency currency = new Currency(CurrencyCode.CAD, CurrencyCode.CAD.name, false, false);
        Currency currency1 = currencyRepository.save(currency);
        currency1.setFree(true);
        currency1 = currencyRepository.save(currency1);
        assertTrue(currency1.isFree());
        currencyRepository.delete(currency1);
    }

    @Test
    void cannotDeleteAssignedCurrency() {
        Optional<Currency> currency = currencyRepository.findById(CurrencyCode.CRD);
        if(currency.isPresent()){
            Exception exception = assertThrows(DataIntegrityViolationException.class, () -> currencyRepository.delete(currency.get()));
            assertTrue(exception.getMessage().contains("could not execute statement; SQL [n/a]; constraint"));
        } else {
            fail("Currency not found");
        }
    }

}