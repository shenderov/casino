package me.shenderov.casino.exceptions;

public class InvalidGameException extends RuntimeException {
    public InvalidGameException(String message) {
        super(message);
    }
}
