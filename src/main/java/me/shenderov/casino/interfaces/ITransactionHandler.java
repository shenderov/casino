package me.shenderov.casino.interfaces;

import me.shenderov.casino.entities.TransactionEntity;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.exceptions.CasinoSetupException;

import java.util.UUID;

public interface ITransactionHandler {

    TransactionEntity processTransaction(UUID sId, Transaction transaction) throws CasinoSetupException;

}
