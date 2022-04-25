package me.shenderov.casino.games.slots.model;

import me.shenderov.casino.games.slots.constants.SlotsConstants;
import me.shenderov.casino.games.slots.enums.BetType;

import java.util.Objects;
import java.util.Set;

public class WinValidator {

    private static final Set<String> winCombinations = SlotsConstants.PAY_TABLE.keySet();

    public boolean validateCombinationWin(String key){
        return winCombinations.contains(key);
    }

    public boolean isJackpot(String key, BetType betType){
        return (Objects.equals(key, SlotsConstants.JACKPOT_KEY)) && (betType == SlotsConstants.JACKPOT_BET);
    }

    public double getWinSum(String key, BetType betType) {
        if (isJackpot(key, betType))
            return SlotsConstants.JACKPOT;
        else if(validateCombinationWin(key)){
            return SlotsConstants.PAY_TABLE.get(key) * betType.betType;
        }else
            return 0;
    }
}
