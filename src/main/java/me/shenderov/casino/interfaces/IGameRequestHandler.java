package me.shenderov.casino.interfaces;

import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GameInfo;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.exceptions.CasinoSetupException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IGameRequestHandler {

    GameAction getGameAction(
            String sId,
            GetGameActionWrapper wrapper,
            HttpServletResponse response,
            HttpServletRequest request) throws CasinoSetupException;

    GameInfo getGameInfo(String gameId);

    List<GameInfo> getGamesList();

}
