package me.shenderov.casino.repositories;

import me.shenderov.casino.entities.GameInfo;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameInfo, String> {

}
