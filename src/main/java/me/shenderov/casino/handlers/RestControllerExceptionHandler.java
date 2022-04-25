package me.shenderov.casino.handlers;

import me.shenderov.casino.entities.ExceptionWrapper;
import me.shenderov.casino.exceptions.InvalidGameException;
import me.shenderov.casino.exceptions.InvalidSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({InvalidSessionException.class})
    public ResponseEntity<ExceptionWrapper> badSessionRequest(HttpServletRequest req, InvalidSessionException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWrapper(HttpStatus.BAD_REQUEST, exception, req.getRequestURI()));
    }

    @ExceptionHandler({InvalidGameException.class})
    public ResponseEntity<ExceptionWrapper> badGameRequest(HttpServletRequest req, InvalidGameException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWrapper(HttpStatus.BAD_REQUEST, exception, req.getRequestURI()));
    }

}
