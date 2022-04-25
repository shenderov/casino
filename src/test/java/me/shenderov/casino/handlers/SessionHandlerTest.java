package me.shenderov.casino.handlers;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.exceptions.InvalidSessionException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionHandlerTest extends CasinoApplicationTests {

    @Test
    void getSession() {
        Session session = sessionHandler.getSession((UUID) null);
        assertNotNull(session.getuUID());
        assertEquals(session.getName(), "Guest");
        assertEquals(session.getBalance(), 1000);
        assertTrue(session.getExpDate() > 0);
        Session session1 = sessionHandler.getSession(session.getuUID());
        assertEquals(session.getuUID(), session1.getuUID());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void getSessionNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionHandler.getSession(UUID.randomUUID()));
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void resetBalance() {
        Session session = sessionHandler.getSession((UUID) null);
        session.setBalance(500.0);
        sessionRepository.save(session);
        session = sessionHandler.getSession(session.getuUID());
        assertEquals(500, session.getBalance());
        Session session1 = sessionHandler.resetBalance(session.getuUID().toString());
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals(session.getName(), session1.getName());
        assertEquals(1000, session1.getBalance());
        assertTrue(session.getExpDate() < session1.getExpDate(), String.format("%d not equals %d", session.getExpDate(), session1.getExpDate()));
    }

    @Test
    void resetBalanceNegative() {
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> sessionHandler.resetBalance(null));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionHandler.resetBalance(UUID.randomUUID().toString()));
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void setName() {
        Session session = sessionHandler.getSession((UUID) null);
        Session session1 = sessionHandler.setName(session.getuUID().toString(), "Joe Doe");
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals("Joe Doe", session1.getName());
        assertEquals(session.getBalance(), session1.getBalance());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void setNameNegative() {
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> sessionHandler.setName(null, "Joe Doe"));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionHandler.setName(UUID.randomUUID().toString(), "Joe Doe"));
        assertEquals("Session not found", exception.getMessage());
        Session session = sessionHandler.getSession((UUID) null);
        exception = assertThrows(TransactionSystemException.class, () -> sessionHandler.setName(session.getuUID().toString(), null));
        assertEquals("Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction", exception.getMessage());
        exception = assertThrows(TransactionSystemException.class, () -> sessionHandler.setName(session.getuUID().toString(), ""));
        assertEquals("Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction", exception.getMessage());
    }

    @Test
    void authCheck() {
        Session session = sessionHandler.getSession((UUID) null);
        Session session1 = sessionHandler.authCheck(session.getuUID());
        assertEquals(session.getuUID(), session1.getuUID());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void authCheckNegative() {
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> sessionHandler.resetBalance(null));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionHandler.resetBalance(UUID.randomUUID().toString()));
        assertEquals("Session not found", exception.getMessage());
        Session session = sessionHandler.getSession((UUID) null);
        session.setExpDate(System.currentTimeMillis());
        sessionRepository.save(session);
        exception = assertThrows(InvalidSessionException.class, () -> sessionHandler.resetBalance(session.getuUID().toString()));
        assertEquals("Session expired", exception.getMessage());
    }
}