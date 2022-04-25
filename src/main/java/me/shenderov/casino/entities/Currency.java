package me.shenderov.casino.entities;

import me.shenderov.casino.enums.CurrencyCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Currency implements Serializable {
    @Id
    private CurrencyCode currencyCode;
    @Column(nullable = false)
    private String currencyName;
    @Column(nullable = false)
    private Boolean isVirtual;
    @Column(nullable = false)
    private Boolean isFree;

    public Currency() {
    }

    public Currency(CurrencyCode currencyCode, String currencyName, Boolean isVirtual, Boolean isFree) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.isVirtual = isVirtual;
        this.isFree = isFree;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean isFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return getCurrencyCode() == currency.getCurrencyCode() && getCurrencyName().equals(currency.getCurrencyName()) && isVirtual.equals(currency.isVirtual) && isFree.equals(currency.isFree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrencyCode(), getCurrencyName(), isVirtual, isFree);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode=" + currencyCode +
                ", currencyName='" + currencyName + '\'' +
                ", isVirtual=" + isVirtual +
                ", isFree=" + isFree +
                '}';
    }
}
