package me.shenderov.casino.api;

import me.shenderov.casino.entities.Session;
import me.shenderov.casino.interfaces.ISessionRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * REST API for creation and management of sessions
 */
@RequestMapping(path = "/sms")
@RestController
public class SessionApi {

    private final ISessionRequestHandler sessionRequestHandler;

    @Autowired
    public SessionApi(ISessionRequestHandler sessionRequestHandler) {
        this.sessionRequestHandler = sessionRequestHandler;
    }

    /**
     * Create a new session(if sId empty) or renew an existing
     * @param sId session ID
     * @param response HttpServletResponse
     * @return Session object
     */
    @RequestMapping(value="/getSession", method=RequestMethod.GET)
    Session getSession(@CookieValue(value = "sId", required = false) String sId, HttpServletResponse response){
        return sessionRequestHandler.getSession(sId, response);
    }

    /**
     * Change session name
     * @param sId Session ID
     * @param name Name to be set
     * @return Session object
     */
    @RequestMapping(value="/setName", method=RequestMethod.GET)
    Session setName(@CookieValue(value = "sId") String sId, @RequestParam(name = "name") String name) throws MissingServletRequestParameterException {
        return sessionRequestHandler.setName(sId, name);
    }

    /**
     * Reset session balance to the default value
     * @param sId Session ID
     * @return Session object
     */
    @RequestMapping(value="/resetBalance", method=RequestMethod.GET)
    Session resetBalance(@CookieValue(value = "sId") String sId){
        return sessionRequestHandler.resetBalance(sId);
    }
}
