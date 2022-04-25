package me.shenderov.casino.exceptions;

public class TransactionDeclinedException extends RuntimeException {
    public TransactionDeclinedException(String message) {
        super(message);
    }
}
