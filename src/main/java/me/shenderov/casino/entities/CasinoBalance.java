package me.shenderov.casino.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CasinoBalance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "currency_code", unique = true)
    private Currency currency;
    private double balance;
    private double winBalance;
    private double totalBets;
    private double totalWins;
    private long transactionCounter;
    private long winCounter;

    public CasinoBalance() {
    }

    public CasinoBalance(Currency currency, double balance, double winBalance, double totalBets, double totalWins, long transactionCounter, long winCounter) {
        this.currency = currency;
        this.balance = balance;
        this.winBalance = winBalance;
        this.totalBets = totalBets;
        this.totalWins = totalWins;
        this.transactionCounter = transactionCounter;
        this.winCounter = winCounter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWinBalance() {
        return winBalance;
    }

    public void setWinBalance(double winBalance) {
        this.winBalance = winBalance;
    }

    public double getTotalBets() {
        return totalBets;
    }

    public void setTotalBets(double totalBets) {
        this.totalBets = totalBets;
    }

    public double getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(double totalWins) {
        this.totalWins = totalWins;
    }

    public long getTransactionCounter() {
        return transactionCounter;
    }

    public void setTransactionCounter(long transactionCounter) {
        this.transactionCounter = transactionCounter;
    }

    public long getWinCounter() {
        return winCounter;
    }

    public void setWinCounter(long winCounter) {
        this.winCounter = winCounter;
    }

    @Override
    public String toString() {
        return "CasinoBalance{" +
                "currency=" + currency +
                ", balance=" + balance +
                ", winBalance=" + winBalance +
                ", totalBets=" + totalBets +
                ", totalWins=" + totalWins +
                ", transactionCounter=" + transactionCounter +
                ", winCounter=" + winCounter +
                '}';
    }
}
