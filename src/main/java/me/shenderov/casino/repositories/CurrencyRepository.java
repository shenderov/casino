package me.shenderov.casino.repositories;

import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.enums.CurrencyCode;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, CurrencyCode> {

}
