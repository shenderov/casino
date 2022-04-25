package me.shenderov.casino.games.dummy.controller;

import java.util.HashMap;
import java.util.Map;

import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.interfaces.IGame;

public class DummyController implements IGame {

    public GameAction getGameAction(Transaction transaction, Map<String, Object> parameters) {
        double bet = 3;
        double win = 3;
        if(parameters != null){
            bet = Double.parseDouble(String.valueOf(parameters.get("bet")));
            win = Double.parseDouble(String.valueOf(parameters.get("win")));
        }
        Map <String, String> action = new HashMap<>();
        action.put("Action1", "Dummy action 1");
        action.put("Action2", "Dummy action 2");
        action.put("Action3", "Dummy action 3");
        transaction.setWin(win);
        transaction.setBet(bet);
        return new GameAction(action, transaction);
    }

    @Override
    public double getBaseBet() {
        return 3;
    }

}

