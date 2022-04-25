package me.shenderov.casino.api;

import me.shenderov.casino.CasinoApplicationTests;
import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.GameAction;
import me.shenderov.casino.entities.GetGameActionWrapper;
import me.shenderov.casino.exceptions.CasinoSetupException;
import me.shenderov.casino.games.slots.entities.Responce;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SlotsTest extends CasinoApplicationTests {



}