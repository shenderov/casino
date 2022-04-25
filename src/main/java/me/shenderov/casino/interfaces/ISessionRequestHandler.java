package me.shenderov.casino.interfaces;

import me.shenderov.casino.entities.Session;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.http.HttpServletResponse;

public interface ISessionRequestHandler {

    Session getSession(String sId, HttpServletResponse response);
    Session setName(String sId, String name) throws MissingServletRequestParameterException;
    Session resetBalance(String sId);

}
