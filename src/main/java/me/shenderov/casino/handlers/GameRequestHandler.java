package me.shenderov.casino.handlers;

import me.shenderov.casino.entities.GameInfo;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.exceptions.InvalidGameException;
import me.shenderov.casino.interfaces.*;
import me.shenderov.casino.repositories.GameRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameRequestHandler implements IGameRequestHandler {

    private final IGameActionHandler gameActionHandler;

    private final ISessionHandler sessionHandler;

    private final GameRepository gameRepository;

    public GameRequestHandler(IGameActionHandler gameActionHandler, ISessionHandler sessionHandler, GameRepository gameRepository) {
        this.gameActionHandler = gameActionHandler;
        this.sessionHandler = sessionHandler;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameAction getGameAction(
            String sId,
            GetGameActionWrapper wrapper,
            HttpServletResponse response,
            HttpServletRequest request) throws CasinoSetupException {
        Session session = sessionHandler.getSession(sId, true);
        return gameActionHandler.getGameAction(session, wrapper);
    }

    @Override
    public GameInfo getGameInfo(String gameId) {
        Optional<GameInfo> gameInfo = gameRepository.findById(gameId);
        if(gameInfo.isEmpty() || !gameInfo.get().isEnabled()){
            throw new InvalidGameException(String.format("Game with ID %s is not found or disabled", gameId));
        }
        return gameInfo.get();
    }

    @Override
    public List<GameInfo> getGamesList() {
        List<GameInfo> gamesList = (List<GameInfo>) gameRepository.findAll();
        return gamesList.stream().filter(GameInfo::isEnabled).collect(Collectors.toList());
    }
}
