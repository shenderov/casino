package me.shenderov.casino;

import me.shenderov.casino.api.GameApi;
import me.shenderov.casino.api.SessionApi;
import me.shenderov.casino.config.ApplicationConfig;
import me.shenderov.casino.handlers.GameRequestHandler;
import me.shenderov.casino.interfaces.*;
import me.shenderov.casino.repositories.CasinoBalanceRepository;
import me.shenderov.casino.repositories.CurrencyRepository;
import me.shenderov.casino.repositories.SessionRepository;
import me.shenderov.casino.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ContextConfiguration(classes = {ApplicationConfig.class, CasinoApplication.class})
@TestPropertySource(locations="classpath:test.properties")
public abstract class CasinoApplicationTests {

    protected final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
    protected final HttpServletRequest httpServletRequest = new MockHttpServletRequest();

    @Autowired
    protected ISessionRequestHandler sessionRequestHandler;
    @Autowired
    protected ISessionHandler sessionHandler;
    @Autowired
    protected SessionApi sessionApi;
    @Autowired
    protected GameApi gameApi;
    @Autowired
    protected IGameRequestHandler gameRequestHandler;
    @Autowired
    protected IGameActionHandler gameActionHandler;
    @Autowired
    protected ITransactionHandler transactionHandler;
    @Autowired
    protected SessionRepository sessionRepository;
    @Autowired
    protected CurrencyRepository currencyRepository;
    @Autowired
    protected CasinoBalanceRepository casinoBalanceRepository;
    @Autowired
    protected TransactionRepository transactionRepository;

    @Test
    void contextLoads() {
    }

}
