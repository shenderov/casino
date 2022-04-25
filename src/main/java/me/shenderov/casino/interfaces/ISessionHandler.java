package me.shenderov.casino.interfaces;

import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;

import java.util.UUID;

public interface ISessionHandler {

    Session getSession(UUID uuid);
    Session getSession(UUID uuid, Boolean doNotCreateIfNotExist);
    Session getSession(String sId);
    Session getSession(String sId, Boolean doNotCreateIfNotExist);
    Session getSession(UUID uuid, Currency currency, Boolean doNotCreateIfNotExist);
    Session resetBalance(String sId);
    Session setName(String sId, String name);
    Session authCheck(UUID uuid);
    Session authCheck(String sId);

}
