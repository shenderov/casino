package me.shenderov.casino.handlers;

import me.shenderov.casino.entities.CasinoBalance;
import me.shenderov.casino.entities.Currency;
import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.TransactionEntity;
import me.shenderov.casino.entities.Transaction;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.exceptions.*;
import me.shenderov.casino.interfaces.ITransactionHandler;
import me.shenderov.casino.repositories.CasinoBalanceRepository;
import me.shenderov.casino.repositories.SessionRepository;
import me.shenderov.casino.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionHandler implements ITransactionHandler {

    private final CasinoBalanceRepository casinoBalanceRepository;

    private final SessionRepository sessionRepository;

    private final TransactionRepository transactionRepository;

    @Value( "${application.casino.validate-transactions}" )
    private boolean VALIDATE_TRANSACTIONS;

    @Value( "${application.casino.transaction-fee-percent}" )
    private int TRANSACTION_FEE;

    public TransactionHandler(CasinoBalanceRepository casinoBalanceRepository, SessionRepository sessionRepository, TransactionRepository transactionRepository) {
        this.casinoBalanceRepository = casinoBalanceRepository;
        this.sessionRepository = sessionRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(rollbackOn = {SessionOutOfBalanceException.class, TransactionDeclinedException.class, CasinoOutOfBalanceException.class})
    public TransactionEntity processTransaction(UUID sId, Transaction transaction) throws CasinoSetupException {
        Session session = getSession(sId);
        validateSession(session, transaction);
        CasinoBalance balance = getCasinoBalance(transaction.getCurrency());
        validateTransaction(balance, transaction);
        if(transaction.getStatus() == TransactionStatus.DECLINED){
            throw new CasinoOutOfBalanceException("Casino out of win balance");
        }
        try {
            double fee = transaction.getBet()*(TRANSACTION_FEE/100F);
            balance.setBalance(balance.getBalance() + fee);
            balance.setWinBalance(balance.getWinBalance() + (transaction.getBet()-fee) - transaction.getWin());
            balance.setTotalWins(balance.getTotalWins()+transaction.getWin());
            balance.setTotalBets(balance.getTotalBets()+transaction.getBet());
            balance.setTransactionCounter(balance.getTransactionCounter() + 1);
            if(transaction.getWin() > 0){
                balance.setWinCounter(balance.getWinCounter() + 1);
            }
            casinoBalanceRepository.save(balance);
            session.setBalance(session.getBalance() + transaction.getWin() - transaction.getBet());
            session = sessionRepository.save(session);
            transaction.setStatus(TransactionStatus.PROCESSED);
            TransactionEntity transactionEntity = new TransactionEntity(session, transaction, session.getBalance());
            return transactionRepository.save(transactionEntity);
        }catch (Exception e){
            e.printStackTrace();
            throw new TransactionDeclinedException("Transaction declined");
        }
    }

    private void validateTransaction(CasinoBalance balance, Transaction transaction) {
        if(balance.getWinBalance() >= transaction.getWin() || !VALIDATE_TRANSACTIONS)
            transaction.setStatus(TransactionStatus.ACCEPTED);
        else
            transaction.setStatus(TransactionStatus.DECLINED);
    }

    private Session getSession(UUID sId) {
        Optional<Session> sessionOptional =  sessionRepository.findById(sId);
        Session session;
        if(sessionOptional.isEmpty()){
            throw new InvalidSessionException("Session not found");
        } else {
            session = sessionOptional.get();
        }
        return session;
    }

    private void validateSession(Session session, Transaction transaction) {
        if(session.getBalance() < transaction.getBet()){
            throw new SessionOutOfBalanceException("Session out of balance");
        }
        if(!session.getCurrency().getCurrencyCode().equals(transaction.getCurrency().getCurrencyCode())){
            throw new SessionOutOfBalanceException("Session currency doesn't match transaction currency");
        }
    }

    private CasinoBalance getCasinoBalance(Currency currency) throws CasinoSetupException {
        Optional<CasinoBalance> balanceOptional = casinoBalanceRepository.findByCurrency(currency);
        if(balanceOptional.isEmpty()){
            throw new CasinoSetupException("Casino balance is not set for the requested currency");
        }else{
            return balanceOptional.get();
        }
    }
}
