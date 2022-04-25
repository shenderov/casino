package me.shenderov.casino.exceptions;

public class SessionOutOfBalanceException extends RuntimeException {
    public SessionOutOfBalanceException(String message) {
        super(message);
    }
}
