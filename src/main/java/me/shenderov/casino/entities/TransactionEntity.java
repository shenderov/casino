package me.shenderov.casino.entities;

import me.shenderov.casino.entities.Session;
import me.shenderov.casino.entities.Transaction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "session_uuid")
    private Session session;
    @Embedded
    @NotNull
    @Column(nullable = false)
    private Transaction transaction;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private Long timestamp = System.currentTimeMillis();

    public TransactionEntity() {
    }

    public TransactionEntity(Session session, Transaction transaction, Double balance) {
        this.session = session;
        this.transaction = transaction;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionDAO{" +
                "id=" + id +
                ", session='" + session + '\'' +
                ", transaction=" + transaction +
                ", balance=" + balance +
                ", timestamp=" + timestamp +
                '}';
    }
}
