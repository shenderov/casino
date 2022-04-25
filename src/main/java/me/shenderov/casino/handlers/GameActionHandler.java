package me.shenderov.casino.handlers;

import me.shenderov.casino.entities.*;
import me.shenderov.casino.enums.TransactionStatus;
import me.shenderov.casino.exceptions.*;
import me.shenderov.casino.interfaces.*;
import me.shenderov.casino.repositories.GameRepository;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameActionHandler implements IGameActionHandler {

    private final ApplicationContext ctx;

    private final ITransactionHandler transactionHandler;

    private final GameRepository gameRepository;

    @Value( "${application.casino.process-transactions}" )
    private boolean PROCESS_TRANSACTIONS = false;

    public GameActionHandler(ApplicationContext ctx, ITransactionHandler transactionHandler, GameRepository gameRepository) {
        this.ctx = ctx;
        this.transactionHandler = transactionHandler;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameAction getGameAction(Session session, GetGameActionWrapper wrapper) throws CasinoSetupException {
        IGame game = getGame(wrapper.getGameId());
        GameAction gameAction = new GameAction();
        TransactionEntity transactionEntity;
        int getGameActionRetryCounter = 0;
        do {
            getGameActionRetryCounter++;
            try{
                gameAction = game.getGameAction(new Transaction(session.getCurrency()), wrapper.getParameters());
                if(PROCESS_TRANSACTIONS){
                    transactionEntity = transactionHandler.processTransaction(session.getuUID(), gameAction.getTransaction());
                    gameAction.setTransaction(transactionEntity.getTransaction());
                }else {
                    Transaction t = gameAction.getTransaction();
                    t.setStatus(TransactionStatus.PROCESSED);
                    gameAction.setTransaction(t);
                }
            } catch (TransactionDeclinedException | CasinoOutOfBalanceException e1) {
                gameAction.getTransaction().setStatus(TransactionStatus.DECLINED);
            } catch (SessionOutOfBalanceException e) {
                gameAction.getTransaction().setStatus(TransactionStatus.OUT_OF_BALANCE);
                break;
            } catch (CasinoSetupException e) {
                e.printStackTrace();
                throw e;
            }
        } while(gameAction.getTransaction().getStatus() != TransactionStatus.PROCESSED &&
                gameAction.getTransaction().getStatus() != TransactionStatus.OUT_OF_BALANCE &&
                getGameActionRetryCounter < 100);
        return gameAction;
    }

    @Override
    public IGame getGame(String game_id) {
        try {
            IGame game = (IGame) ctx.getBean(game_id);
            Optional<GameInfo> gameInfo = gameRepository.findById(game_id);
            if(gameInfo.isEmpty() || !gameInfo.get().isEnabled()){
                throw new InvalidGameException(String.format("Game with ID %s is not found or disabled", game_id));
            }
            return  game;
        } catch (NoSuchBeanDefinitionException e){
            e.printStackTrace();
            throw new InvalidGameException(String.format("Game with ID %s is not configured", game_id));
        }
    }
}
