package me.shenderov.casino.handlers;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.exceptions.InvalidGameException;
import me.shenderov.casino.interfaces.IGame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameActionHandlerTest extends CasinoApplicationTests {

    @Test
    void getGameAction() throws CasinoSetupException {
        Session session = sessionHandler.getSession((UUID) null);
        GetGameActionWrapper wrapper = new GetGameActionWrapper("dummy", null);
        GameAction gameAction = gameActionHandler.getGameAction(session, wrapper);
        assertEquals(gameAction.getTransaction().getCurrency().getCurrencyCode(), session.getCurrency().getCurrencyCode());
        assertEquals(gameAction.getTransaction().getWin(), 3);
        assertEquals(gameAction.getTransaction().getBet(), 3);
        assertEquals(gameAction.getTransaction().getStatus(), TransactionStatus.PROCESSED);
        Map<String, String> action = (Map<String, String>) gameAction.getGameAction();
        assertEquals(action.get("Action1"), "Dummy action 1");
        assertEquals(action.get("Action2"), "Dummy action 2");
        assertEquals(action.get("Action3"), "Dummy action 3");
        Session session1 = sessionHandler.getSession(session.getuUID());
        assertEquals(session1.getBalance(), 1000);
    }

    @Test
    void getGame() {
        IGame game = gameActionHandler.getGame("dummy");
        assertNotNull(game);
    }

    @Test
    void getGameNegative() {
        Exception exception = assertThrows(InvalidGameException.class, () -> gameActionHandler.getGame("invalid"));
        assertEquals("Game with ID invalid is not configured", exception.getMessage());
    }
}