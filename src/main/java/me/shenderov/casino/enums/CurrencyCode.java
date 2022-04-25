package me.shenderov.casino.enums;

public enum CurrencyCode {

    CRD("Credit"),
    USD("US Dollar"),
    CAD("Canadian Dollar");

    public final String name;

    CurrencyCode(String name) {
        this.name = name;
    }
}
