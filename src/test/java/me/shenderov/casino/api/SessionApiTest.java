package me.shenderov.casino.api;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.exceptions.InvalidSessionException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionApiTest extends CasinoApplicationTests {

    @Test
    void getSession() {
        Session session = sessionApi.getSession(null, httpServletResponse);
        assertNotNull(session.getuUID());
        assertEquals(session.getName(), "Guest");
        assertEquals(session.getBalance(), 1000);
        assertTrue(session.getExpDate() > 0);
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session.getuUID(), session1.getuUID());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void getSessionNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionApi.getSession("invalid", httpServletResponse));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionApi.getSession(UUID.randomUUID().toString(), httpServletResponse));
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void getSessionExpiredNegative() {
        Session session = sessionApi.getSession(null, httpServletResponse);
        session.setExpDate(session.getExpDate() - TimeUnit.DAYS.toMillis(50));
        Session finalSession = sessionRepository.save(session);
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionApi.getSession(finalSession.getuUID().toString(), httpServletResponse));
        assertEquals("Session expired", exception.getMessage());
    }

    @Test
    void setName() throws MissingServletRequestParameterException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        Session session1 = sessionApi.setName(session.getuUID().toString(), "Joe Doe");
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals("Joe Doe", session1.getName());
        assertEquals(session.getBalance(), session1.getBalance());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void setNameExpiredSessionNegative() {
        Session session = sessionApi.getSession(null, httpServletResponse);
        session.setExpDate(session.getExpDate() - TimeUnit.DAYS.toMillis(50));
        Session finalSession = sessionRepository.save(session);
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionApi.setName(finalSession.getuUID().toString(), "Joe Doe"));
        assertEquals("Session expired", exception.getMessage());
    }

    @Test
    void setNameNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionApi.setName("invalid", "Joe Doe"));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionApi.setName(UUID.randomUUID().toString(), "Joe Doe"));
        assertEquals("Session not found", exception.getMessage());
        Session session = sessionApi.getSession(null, httpServletResponse);
        exception = assertThrows(MissingServletRequestParameterException.class, () -> sessionApi.setName(session.getuUID().toString(), null));
        assertEquals("Required request parameter 'name' for method parameter type String is not present", exception.getMessage());
        exception = assertThrows(MissingServletRequestParameterException.class, () -> sessionApi.setName(session.getuUID().toString(), ""));
        assertEquals("Required request parameter 'name' for method parameter type String is not present", exception.getMessage());
        exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> sessionApi.setName(null, "Joe"));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionApi.setName("", "Joe"));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
    }

    @Test
    void resetBalance() {
        Session session = sessionApi.getSession(null, httpServletResponse);
        session.setBalance(500.0);
        sessionRepository.save(session);
        session = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(500, session.getBalance());
        Session session1 = sessionApi.resetBalance(session.getuUID().toString());
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals(session.getName(), session1.getName());
        assertEquals(1000, session1.getBalance());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void resetBalanceExpiredSessionNegative() {
        Session session = sessionApi.getSession(null, httpServletResponse);
        session.setExpDate(session.getExpDate() - TimeUnit.DAYS.toMillis(50));
        Session finalSession = sessionRepository.save(session);
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionApi.resetBalance(finalSession.getuUID().toString()));
        assertEquals("Session expired", exception.getMessage());
    }

    @Test
    void resetBalanceNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionApi.resetBalance("invalid"));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionApi.resetBalance(UUID.randomUUID().toString()));
        assertEquals("Session not found", exception.getMessage());
        exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> sessionApi.resetBalance(null));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionApi.resetBalance(""));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
    }
}