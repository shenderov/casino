package me.shenderov.casino.repositories;

import me.shenderov.casino.entities.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {

}
