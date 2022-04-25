package me.shenderov.casino.repositories;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.enums.CurrencyCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionRepositoryTest extends CasinoApplicationTests {

    @Test
    void saveSession() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        Session session1 = sessionRepository.save(session);
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals(session.getName(), session1.getName());
        assertEquals(session.getBalance(), session1.getBalance());
        assertEquals(session.getExpDate(), session1.getExpDate());
    }

    @Test
    void changeChangeableFields() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        session.setBalance(2000.0);
        session.setName("Name");
        session.setExpDate(112233344L);
        session = sessionRepository.save(session);
        assertEquals("Name", session.getName());
        assertEquals(2000, session.getBalance());
        assertEquals(112233344, session.getExpDate());
    }

    @Test
    void uuidCantBeNull() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(null, "Joe", currency, 1000.0, System.currentTimeMillis());
        Exception exception = assertThrows(JpaSystemException.class, () -> sessionRepository.save(session));
        assertEquals("ids for this class must be manually assigned before calling save(): me.shenderov.casino.entities.Session; nested exception is org.hibernate.id.IdentifierGenerationException: ids for this class must be manually assigned before calling save(): me.shenderov.casino.entities.Session", exception.getMessage());
    }

    @Test
    void nameCantBeNull() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), null, currency, 1000.0, System.currentTimeMillis());
        Exception exception = assertThrows(TransactionSystemException.class, () -> sessionRepository.save(session));
        assertEquals("Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction", exception.getMessage());
    }

    @Test
    void currencyCantBeNull() {
        Session session = new Session(UUID.randomUUID(), "Joe", null, 1000.0, System.currentTimeMillis());
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> sessionRepository.save(session));
        assertEquals("could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement", exception.getMessage());
    }

    @Test
    void balanceCantBeNull() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, null, System.currentTimeMillis());
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> sessionRepository.save(session));
        assertEquals("could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement", exception.getMessage());
    }

    @Test
    void expDateCantBeNull() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, null);
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> sessionRepository.save(session));
        assertEquals("could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement", exception.getMessage());
    }

    @Test
    void changeUUID() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        session.setuUID(UUID.randomUUID());
        sessionRepository.save(session);
    }

    @Test
    void changeCurrency() {
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Currency usd = new Currency(CurrencyCode.USD, "US Dollar", false, false);
        currencyRepository.save(usd);
        Session session = new Session(UUID.randomUUID(), "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        session.setCurrency(usd);
        sessionRepository.save(session);
    }

    @Test
    void deleteSession() {
        UUID uuid = UUID.randomUUID();
        Currency currency = new Currency(CurrencyCode.CRD, "Credit", true, true);
        Session session = new Session(uuid, "Joe", currency, 1000.0, System.currentTimeMillis());
        session = sessionRepository.save(session);
        sessionRepository.delete(session);
        assertFalse(sessionRepository.existsById(uuid));
        assertTrue(currencyRepository.existsById(CurrencyCode.CRD));
    }

}