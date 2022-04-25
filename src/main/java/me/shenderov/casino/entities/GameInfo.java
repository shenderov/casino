package me.shenderov.casino.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GameInfo {
    @Id
    private String id;
    private String name;
    private String description;
    private double baseBetPrice;
    private boolean enabled;

    public GameInfo() {
    }

    public GameInfo(String id, String name, String description, double baseBetPrice, boolean enabled) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseBetPrice = baseBetPrice;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBaseBetPrice() {
        return baseBetPrice;
    }

    public void setBaseBetPrice(double baseBetPrice) {
        this.baseBetPrice = baseBetPrice;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", baseBetPrice=" + baseBetPrice +
                ", enabled=" + enabled +
                '}';
    }
}
