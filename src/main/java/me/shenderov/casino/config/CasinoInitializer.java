package me.shenderov.casino.config;

import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.GameInfo;
import me.shenderov.casino.enums.CurrencyCode;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.interfaces.IGame;
import me.shenderov.casino.interfaces.IGameActionHandler;
import me.shenderov.casino.repositories.CasinoBalanceRepository;
import me.shenderov.casino.repositories.CurrencyRepository;
import me.shenderov.casino.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

public class CasinoInitializer {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CasinoBalanceRepository casinoBalanceRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private IGameActionHandler gameActionHandler;

    @PostConstruct
    public void initiateData() throws CasinoSetupException {
        if (currencyRepository.count() == 0) {
            insertDefaultCurrency();
        }
        if (casinoBalanceRepository.count() == 0) {
            createDefaultBalance();
        }
        if (gameRepository.count() == 0) {
            insertGamesInfo();
        }
    }

    private void insertDefaultCurrency()  {
        Currency currency = new Currency(CurrencyCode.CRD, CurrencyCode.CRD.name, true, true);
        currencyRepository.save(currency);
    }

    private void createDefaultBalance() throws CasinoSetupException {
        Optional<Currency> currency = currencyRepository.findById(CurrencyCode.CRD);
        if(currency.isEmpty()){
            throw new CasinoSetupException("Cannot create default balance due to missing default currency");
        }
        CasinoBalance balance = new CasinoBalance();
        balance.setCurrency(currency.get());
        balance.setWinBalance(1000.0);
        casinoBalanceRepository.save(balance);
    }

    private void insertGamesInfo()  {
        String dummyId = "dummy";
        String slotsId = "slots";
        GameInfo dummyInfo = new GameInfo(dummyId, "Dummy Game", "Dummy game for debugging and testing", 1, true);
        GameInfo slotsInfo = new GameInfo(slotsId, "Slots Game", "Cherry Slots", 1, true);
        dummyInfo = gameRepository.save(dummyInfo);
        slotsInfo = gameRepository.save(slotsInfo);
        IGame dummy = gameActionHandler.getGame(dummyId);
        IGame slots = gameActionHandler.getGame(slotsId);
        IGame demo = gameActionHandler.getGame(slotsId);
        dummyInfo.setBaseBetPrice(dummy.getBaseBet());
        slotsInfo.setBaseBetPrice(slots.getBaseBet());
        gameRepository.save(dummyInfo);
        gameRepository.save(slotsInfo);
    }
}
