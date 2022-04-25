package me.shenderov.casino.handlers;

import me.shenderov.casino.entities.Session;
import me.shenderov.casino.interfaces.ISessionHandler;
import me.shenderov.casino.interfaces.ISessionRequestHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class SessionRequestHandler implements ISessionRequestHandler {
    private final ISessionHandler sessionHandler;

    public SessionRequestHandler(ISessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    @Override
    public Session getSession(String sId, HttpServletResponse response) {
        Session session = sessionHandler.getSession(sId);
        Cookie cookie = new Cookie("sId", session.getuUID().toString());
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30));
        cookie.setPath("/");
        response.addCookie(cookie);
        return session;
    }

    @Override
    public Session setName(String sId, String name) throws MissingServletRequestParameterException {
        if(name == null || name.isEmpty()){
            throw new MissingServletRequestParameterException("name", "String");
        }
        return sessionHandler.setName(sId, name);
    }

    @Override
    public Session resetBalance(String sId) {
        return sessionHandler.resetBalance(sId);
    }
}
