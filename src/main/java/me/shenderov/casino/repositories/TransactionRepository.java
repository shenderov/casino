package me.shenderov.casino.repositories;

import me.shenderov.casino.entities.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
