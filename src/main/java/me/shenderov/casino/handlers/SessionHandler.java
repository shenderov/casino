package me.shenderov.casino.handlers;

import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.enums.CurrencyCode;
import me.shenderov.casino.exceptions.InvalidCurrencyException;
import me.shenderov.casino.exceptions.InvalidSessionException;
import me.shenderov.casino.interfaces.ISessionHandler;
import me.shenderov.casino.repositories.CurrencyRepository;
import me.shenderov.casino.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SessionHandler implements ISessionHandler {

    private final CurrencyRepository currencyRepository;

    private final SessionRepository sessionRepository;

    @Value( "${application.session.default-balance}" )
    private Double DEFAULT_BALANCE;

    @Value( "${application.session.default-username}" )
    private String DEFAULT_NAME;

    @Value( "${application.session.max-age-days}" )
    private int SESSION_MAX_AGE_DAYS;

    public SessionHandler(CurrencyRepository currencyRepository, SessionRepository sessionRepository) {
        this.currencyRepository = currencyRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session getSession(UUID uuid) {
        return getSession(uuid,false);
    }

    @Override
    public Session getSession(UUID uuid, Boolean doNotCreateIfNotExist) {
        Optional<Currency> defaultCurrency = currencyRepository.findById(CurrencyCode.CRD);
        if(defaultCurrency.isEmpty()){
            throw new InvalidCurrencyException(String.format("Default currency for code %s is not found", CurrencyCode.CRD));
        }
        return getSession(uuid, defaultCurrency.get(), doNotCreateIfNotExist);
    }

    @Override
    public Session getSession(String sId) {
        return getSession(sId, false);
    }

    @Override
    public Session getSession(String sId, Boolean doNotCreateIfNotExist) {
        UUID uuid = null;
        if(sId != null){
            try {
                uuid = UUID.fromString(sId);
            } catch (IllegalArgumentException e) {
                throw new InvalidSessionException("Cannot create UUID from provided string");
            }
        }
        return getSession(uuid, doNotCreateIfNotExist);
    }

    @Override
    public Session getSession(UUID uuid, Currency currency, Boolean doNotCreateIfNotExist) {
        Session session;
        long expDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(SESSION_MAX_AGE_DAYS);
        if (uuid == null && !doNotCreateIfNotExist) {
            session = new Session(UUID.randomUUID(), DEFAULT_NAME, currency, DEFAULT_BALANCE, expDate);
            if(!sessionRepository.existsById(session.getuUID())){
                session = sessionRepository.save(session);
            } else {
                throw new RuntimeException("Fail to create a new session");
            }
        }else {
            session = authCheck(uuid);
        }
        return session;
    }

    @Override
    public Session resetBalance(String sId){
        Session session = authCheck(sId);
        session.setBalance(DEFAULT_BALANCE);
        session = sessionRepository.save(session);
        return session;
    }

    @Override
    public Session setName(String sId, String name) {
        Session session = authCheck(sId);
        session.setName(name);
        session = sessionRepository.save(session);
        return session;
    }

    @Override
    public Session authCheck(UUID uuid) {
        Session session = getSessionById(uuid);
        if(session.getExpDate() < System.currentTimeMillis()){
            throw new InvalidSessionException("Session expired");
        }else {
            long expDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(SESSION_MAX_AGE_DAYS);
            session.setExpDate(expDate);
            session = sessionRepository.save(session);
        }
        return session;
    }

    @Override
    public Session authCheck(String sId) {
        UUID uuid = null;
        if(sId != null){
            try {
                uuid = UUID.fromString(sId);
            } catch (IllegalArgumentException e) {
                throw new InvalidSessionException("Cannot create UUID from provided string");
            }
        }
        return authCheck(uuid);
    }

    private Session getSessionById(UUID uuid){
        Optional<Session> s = sessionRepository.findById(uuid);
        if(s.isPresent()){
            return s.get();
        }else {
            throw new InvalidSessionException("Session not found");
        }
    }
}
