package me.shenderov.casino.entities;

import me.shenderov.casino.enums.TransactionStatus;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Transaction {
    @ManyToOne
    @JoinColumn(name = "currency_currency_code")
    private Currency currency;
    private Double bet;
    private Double win;
    private TransactionStatus status;

    public Transaction() {
    }

    public Transaction(Currency currency) {
        this.currency = currency;
        this.status = TransactionStatus.NEW;
    }

    public Transaction(Currency currency, Double bet, Double win, TransactionStatus status) {
        this.currency = currency;
        this.bet = bet;
        this.win = win;
        this.status = status;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBet() {
        return bet;
    }

    public void setBet(Double bet) {
        this.bet = bet;
    }

    public Double getWin() {
        return win;
    }

    public void setWin(Double win) {
        this.win = win;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "bet=" + bet +
                ", win=" + win +
                ", status=" + status +
                '}';
    }
}
