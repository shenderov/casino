package me.shenderov.casino.exceptions;

public class CasinoOutOfBalanceException extends RuntimeException {
    public CasinoOutOfBalanceException(String message) {
        super(message);
    }
}
