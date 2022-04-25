package me.shenderov.casino.games.slots.controller;

import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.games.slots.constants.SlotsConstants;
import me.shenderov.casino.games.slots.entities.Reels;
import me.shenderov.casino.games.slots.entities.Responce;
import me.shenderov.casino.games.slots.enums.BetType;
import me.shenderov.casino.games.slots.model.SlotsGenerator;
import me.shenderov.casino.games.slots.model.WinValidator;
import me.shenderov.casino.interfaces.IGame;

import java.util.Map;

public class SlotsController implements IGame {

    public GameAction getGameAction(Transaction transaction, Map<String, Object> parameters) {
        BetType betType = BetType.X1;
        if(parameters != null){
            betType = BetType.valueOf(String.valueOf(parameters.get("betType")));
        }
        SlotsGenerator generator = new SlotsGenerator();
        WinValidator validator = new WinValidator();
        double bet = SlotsConstants.BASE_BET * betType.betType;
        double win = 0;
        Reels reels = generator.spinReels();
        boolean isWin = validator.validateCombinationWin(reels.getKey());
        if(isWin)
            win = validator.getWinSum(reels.getKey(), betType);
        Responce responce = new Responce();
        responce.setBetType(betType);
        responce.setReels(reels);
        responce.setWin(isWin);
        responce.setJackpot(validator.isJackpot(reels.getKey(), betType));
        transaction.setWin(win);
        transaction.setBet(bet);
        return new GameAction(responce, transaction);
    }

    @Override
    public double getBaseBet() {
        return SlotsConstants.BASE_BET;
    }
}
