package me.shenderov.casino.games.slots.enums;

public enum BetType {

    X1(1),
    X2(2),
    X3(3);

    public final Integer betType;

    BetType(Integer betType) {
        this.betType = betType;
    }
}
