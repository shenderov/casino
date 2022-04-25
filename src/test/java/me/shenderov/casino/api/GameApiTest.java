package me.shenderov.casino.api;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.exceptions.InvalidGameException;
import me.shenderov.casino.exceptions.InvalidSessionException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameApiTest extends CasinoApplicationTests {

    @Test
    void gameAction() throws CasinoSetupException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        GameAction gameAction = gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 3);
        assertEquals(gameAction.getTransaction().getBet(), 3);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.PROCESSED);
        Map<String, String> action = (Map<String, String>) gameAction.getGameAction();
        assertEquals(action.get("Action1"), "Dummy action 1");
        assertEquals(action.get("Action2"), "Dummy action 2");
        assertEquals(action.get("Action3"), "Dummy action 3");
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session1.getBalance(), 1000);
    }

    @Test
    void gameActionNoWin() throws CasinoSetupException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("bet", 3);
        parameters.put("win", 0);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", parameters);
        GameAction gameAction = gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 0);
        assertEquals(gameAction.getTransaction().getBet(), 3);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.PROCESSED);
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session1.getBalance(), 997);
    }

    @Test
    void gameActionWin() throws CasinoSetupException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("bet", 1);
        parameters.put("win", 3);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", parameters);
        GameAction gameAction = gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 3);
        assertEquals(gameAction.getTransaction().getBet(), 1);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.PROCESSED);
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session1.getBalance(), 1002);
    }

    @Test
    void gameActionWinAboveCasinoBalance() throws CasinoSetupException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("bet", 1);
        parameters.put("win", 10000);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", parameters);
        GameAction gameAction = gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 10000);
        assertEquals(gameAction.getTransaction().getBet(), 1);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.DECLINED);
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session1.getBalance(), 1000);
    }

    @Test
    void gameActionSessionOutOfBalance() throws CasinoSetupException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("bet", 10000);
        parameters.put("win", 10);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", parameters);
        GameAction gameAction = gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 10);
        assertEquals(gameAction.getTransaction().getBet(), 10000);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.OUT_OF_BALANCE);
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session1.getBalance(), 1000);
    }

    @Test
    void gameActionZeroCasinoBalance() throws CasinoSetupException {
        Session session = sessionApi.getSession(null, httpServletResponse);
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("bet", 10000);
        parameters.put("win", 10);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", parameters);
        GameAction gameAction = gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 10);
        assertEquals(gameAction.getTransaction().getBet(), 10000);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.OUT_OF_BALANCE);
        Session session1 = sessionApi.getSession(session.getuUID().toString(), httpServletResponse);
        assertEquals(session1.getBalance(), 1000);
    }

    @Test
    void gameActionNoSession() {
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        Exception exception = assertThrows(InvalidSessionException.class, () -> gameApi.gameAction("", wrapper, httpServletResponse, httpServletRequest));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
    }

    @Test
    void gameActionNullSession() {
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> gameApi.gameAction(null, wrapper, httpServletResponse, httpServletRequest));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
    }

    @Test
    void gameActionInvalidSession() {
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        Exception exception = assertThrows(InvalidSessionException.class, () -> gameApi.gameAction("invalid", wrapper, httpServletResponse, httpServletRequest));
        assertEquals("Cannot create UUID from provided string", exception.getMessage());
    }

    @Test
    void gameActionExpiredSession() {
        Session session = sessionApi.getSession(null, httpServletResponse);
        session.setExpDate(session.getExpDate() - TimeUnit.DAYS.toMillis(50));
        Session finalSession = sessionRepository.save(session);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        Exception exception = assertThrows(InvalidSessionException.class, () -> {
            gameApi.gameAction(finalSession.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest);
            sessionApi.getSession(finalSession.getuUID().toString(), httpServletResponse);
        });
        assertEquals("Session expired", exception.getMessage());
    }

    @Test
    void gameActionNotExistingSession() {
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        Exception exception = assertThrows(InvalidSessionException.class, () -> gameApi.gameAction(UUID.randomUUID().toString(), wrapper, httpServletResponse, httpServletRequest));
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void gameActionWrongGameId() {
        Session session = sessionHandler.getSession((UUID) null);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("wrongGameID", null);
        Exception exception = assertThrows(InvalidGameException.class, () -> gameApi.gameAction(session.getuUID().toString(), wrapper, httpServletResponse, httpServletRequest));
        assertEquals("Game with ID wrongGameID is not configured", exception.getMessage());
    }

    @Test
    void gameActionNoRequestBody() {
        Session session = sessionHandler.getSession((UUID) null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameApi.gameAction(session.getuUID().toString(), new GetGameActionWrapper(), httpServletResponse, httpServletRequest));
        assertEquals("'name' must not be null", exception.getMessage());
    }
}