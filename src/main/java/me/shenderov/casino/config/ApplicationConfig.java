package me.shenderov.casino.config;

import me.shenderov.casino.games.dummy.controller.DummyController;
import me.shenderov.casino.games.slots.controller.SlotsController;
import me.shenderov.casino.interfaces.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CasinoInitializer dataInitializer() {
        return new CasinoInitializer();
    }

    @Bean(name = "dummy")
    public IGame getDummyGame() {
        return new DummyController();
    }

    @Bean(name = "slots")
    public IGame getSlotsGame() {
        return new SlotsController();
    }

}
