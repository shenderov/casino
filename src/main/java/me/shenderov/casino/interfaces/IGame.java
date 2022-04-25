package me.shenderov.casino.interfaces;

import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.Transaction;

import java.util.Map;

public interface IGame {

    GameAction getGameAction(Transaction transaction, Map<String, Object> parameters);

    double getBaseBet();

}
