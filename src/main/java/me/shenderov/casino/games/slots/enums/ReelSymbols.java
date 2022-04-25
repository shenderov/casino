package me.shenderov.casino.games.slots.enums;

public enum ReelSymbols {

    BLANK("Blank"),
    RED7("7 Red"),
    WHITE7("7 White"),
    BLUE7("7 Blue"),
    X1_BAR_RED("Bar x1 (Red)"),
    X2_BAR_WHITE("Bar x2 (White)"),
    X3_BAR_BLUE("Bar x3 (Blue)"),
    WILD("Wild");

    public final String textValue;

    ReelSymbols(String textValue) {
        this.textValue = textValue;
    }
}
