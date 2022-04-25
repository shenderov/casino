package me.shenderov.casino.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Session {
    @Id
    @Column(updatable = false, insertable = false, name = "uuid")
    private UUID uUID;
    @NotEmpty
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_currency_code")
    private Currency currency;
    @Column(nullable = false)
    private Double balance;
    @Column(nullable = false)
    private Long expDate;

    public Session() {
    }

    public Session(UUID uUID, String name, Currency currency, Double balance, Long expDate) {
        this.uUID = uUID;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.expDate = expDate;
    }

    public UUID getuUID() {
        return uUID;
    }

    public void setuUID(UUID uUID) {
        this.uUID = uUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getExpDate() {
        return expDate;
    }

    public void setExpDate(Long expDate) {
        this.expDate = expDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return getuUID().equals(session.getuUID()) && getName().equals(session.getName()) && getCurrency().equals(session.getCurrency()) && getBalance().equals(session.getBalance()) && getExpDate().equals(session.getExpDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getuUID(), getName(), getCurrency(), getBalance(), getExpDate());
    }

    @Override
    public String toString() {
        return "Session{" +
                "uUID=" + uUID +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                ", expDate=" + expDate +
                '}';
    }
}
