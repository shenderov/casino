package me.shenderov.casino.api;

import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GameInfo;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.interfaces.IGameRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST APIs for games
 */
@RequestMapping(path = "/game")
@RestController
public class GameApi {

    private final IGameRequestHandler gameRequestHandler;

    @Autowired
    public GameApi(IGameRequestHandler gameRequestHandler) {
        this.gameRequestHandler = gameRequestHandler;
    }

    /**
     *
     * @param sId Session ID for authentication
     * @param wrapper Get game action wrapper for game configuration
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     * @return GameAction object contain game action and transaction
     */
    @RequestMapping(value = "/gameAction", method = RequestMethod.POST)
    public GameAction gameAction(@CookieValue(value = "sId") String sId,
                              @RequestBody @Valid GetGameActionWrapper wrapper,
                              HttpServletResponse response,
                              HttpServletRequest request) throws CasinoSetupException {
        return gameRequestHandler.getGameAction(sId, wrapper, response, request);
    }

    /**
     * Get list of available games
     * @return List of GameInfo objects
     */
    @RequestMapping(value="/games", method=RequestMethod.GET)
    List<GameInfo> getGamesList(){
        return gameRequestHandler.getGamesList();
    }

    /**
     * Get game details
     * @param game_id game ID
     * @return GameInfo object
     */
    @RequestMapping(value="/game", method=RequestMethod.GET)
    GameInfo getGameInfo(@RequestParam(name = "game_id") String game_id){
        return gameRequestHandler.getGameInfo(game_id);
    }

}
