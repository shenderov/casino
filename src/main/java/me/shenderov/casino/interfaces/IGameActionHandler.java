package me.shenderov.casino.interfaces;

import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.exceptions.CasinoSetupException;

public interface IGameActionHandler {

    GameAction getGameAction(Session session, GetGameActionWrapper wrapper) throws CasinoSetupException;
    IGame getGame(String game_id);

}
