package me.shenderov.casino.handlers;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.exceptions.InvalidSessionException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionRequestHandlerTest extends CasinoApplicationTests {

    @Test
    void getSession() {
        Session session = sessionRequestHandler.getSession(null, httpServletResponse);
        assertNotNull(session.getuUID());
        assertEquals(session.getName(), "Guest");
        assertEquals(session.getBalance(), 1000);
        assertTrue(session.getExpDate() > 0);
        Session session1 = sessionRequestHandler.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session.getuUID(), session1.getuUID());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void getSessionNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionRequestHandler.getSession("invalid", httpServletResponse));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionRequestHandler.getSession(UUID.randomUUID().toString(), httpServletResponse));
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void setName() throws MissingServletRequestParameterException {
        Session session = sessionRequestHandler.getSession(null, httpServletResponse);
        Session session1 = sessionRequestHandler.setName(session.getuUID().toString(), "Joe Doe");
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals("Joe Doe", session1.getName());
        assertEquals(session.getBalance(), session1.getBalance());
        assertTrue(session.getExpDate() < session1.getExpDate());
    }

    @Test
    void setNameNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionRequestHandler.setName("invalid", "Joe Doe"));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionRequestHandler.setName(UUID.randomUUID().toString(), "Joe Doe"));
        assertEquals("Session not found", exception.getMessage());
        Session session = sessionRequestHandler.getSession(null, httpServletResponse);
        exception = assertThrows(MissingServletRequestParameterException.class, () -> sessionRequestHandler.setName(session.getuUID().toString(), null));
        assertEquals("Required request parameter 'name' for method parameter type String is not present", exception.getMessage());
        exception = assertThrows(MissingServletRequestParameterException.class, () -> sessionRequestHandler.setName(session.getuUID().toString(), ""));
        assertEquals("Required request parameter 'name' for method parameter type String is not present", exception.getMessage());
    }

    @Test
    void resetBalance() {
        Session session = sessionRequestHandler.getSession(null, httpServletResponse);
        session.setBalance(500.0);
        sessionRepository.save(session);
        session = sessionRequestHandler.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(500, session.getBalance());
        Session session1 = sessionRequestHandler.resetBalance(session.getuUID().toString());
        assertEquals(session.getuUID(), session1.getuUID());
        assertEquals(session.getName(), session1.getName());
        assertEquals(1000, session1.getBalance());
        assertTrue(session.getExpDate() < session1.getExpDate(), String.format("%d not equals %d", session.getExpDate(), session1.getExpDate()));
    }

    @Test
    void resetBalanceNegative() {
        Exception exception = assertThrows(InvalidSessionException.class, () -> sessionRequestHandler.resetBalance("invalid"));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
        exception = assertThrows(InvalidSessionException.class, () -> sessionRequestHandler.resetBalance(UUID.randomUUID().toString()));
        assertEquals("Session not found", exception.getMessage());
    }

}