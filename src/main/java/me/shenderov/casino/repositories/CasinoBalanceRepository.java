package me.shenderov.casino.repositories;

import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CasinoBalanceRepository extends CrudRepository<CasinoBalance, Long> {

    Optional<CasinoBalance> findByCurrency(Currency currency);

}
