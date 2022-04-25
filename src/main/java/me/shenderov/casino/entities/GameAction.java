package me.shenderov.casino.entities;

public class GameAction {
    private Object gameAction;
    private Transaction transaction;

    public GameAction() {
        super();
    }
    public GameAction(Object gameAction, Transaction transaction) {
        super();
        this.gameAction = gameAction;
        this.transaction = transaction;
    }

    public Object getGameAction() {
        return gameAction;
    }

    public void setGameAction(Object gameAction) {
        this.gameAction = gameAction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "GameAction{" +
                "gameAction=" + gameAction +
                ", transaction=" + transaction +
                '}';
    }
}
